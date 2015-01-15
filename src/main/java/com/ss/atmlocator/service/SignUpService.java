package com.ss.atmlocator.service;


import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.entity.UserStatus;
import com.ss.atmlocator.utils.SendMails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.MapBindingResult;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class SignUpService {

    private final String DEFAULT_USER_AVATAR = "defaultUserAvatar.jpg";

    @Autowired
    @Qualifier("mail")
    private SendMails sendMails;

    @Autowired
    private IUsersDAO usersDAO;

    @Autowired
    private NewUserValidatorService validateUserField;

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    public MapBindingResult signUpUser(User user, boolean autoLogin){

        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), user.getClass().getName());

        validateUserField.validate(user,errors);

        if(errors.hasErrors()) {return errors;}

        user.setEnabled(UserStatus.ENABLED);
        user.setAvatar(DEFAULT_USER_AVATAR);

        Role role = usersDAO.getDefaultUserRole();
        Set<Role> roles = new HashSet<Role>(0);
        roles.add(role);
        user.setRoles(roles);

        String password = user.getPassword();

        userService.createUser(user);

        try{
            sendMails.sendMail(user.getEmail(),
                    "User Created",
                    "You create user: "+user.getLogin()+"; with password:"+password);
        }
        catch (MailSendException | MessagingException exp) {
           /*NOP Exception already logged*/
        }

        if(autoLogin){
            loginUser(user.getLogin(),password);
        }

        return null;

    }

    private void loginUser(String login, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

}
