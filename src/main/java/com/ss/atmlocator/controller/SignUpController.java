package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.NewUserValidatorService;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.SendMails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Controller
public class SignUpController {

    final Logger logger = Logger.getLogger(SignUpController.class.getName());

    private final int ENABLED_USER_STATUS = 1;

    private final String DEFAULT_USER_AVATAR = "defaultUserAvatar.jpg";

    @Autowired
    private IUsersDAO usersDAO;

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("mail")
    private SendMails sendMails;

    @Autowired
    private NewUserValidatorService validateUserField;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "signup";
    }


    @RequestMapping(value = "/registering", method = RequestMethod.POST)
    public String registering(@RequestParam(value = "inputLogin",required = false) String login,
                              @RequestParam("inputEmail") String email,
                              @RequestParam(value = "inputPassword",required = false) String password,
                              @RequestParam(value = "signMe", required = false) String signMe,
                              Model model,HttpServletRequest request) {

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);

        logger.info("POST parametr login: " +"{"+login+"}");
        logger.info("POST parametr password: " +"{"+password+"}");
        logger.info("POST parametr email: " +"{"+email+"}");


        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), user.getClass().getName());
        validateUserField.validate(user,errors);


        if(errors.hasErrors()) {
            String errorCause = "";
            for(ObjectError err : errors.getAllErrors()){
                errorCause += err.getCode()+"; ";
            }
            String errorMessage = "You entered invalid  parametrs: "+errorCause;
            model.addAttribute("error", errorMessage);
            return "signup";
        }

        user.setEnabled(ENABLED_USER_STATUS);
        user.setAvatar(DEFAULT_USER_AVATAR);
        Role role = usersDAO.getDefaultUserRole();
        Set<Role> roles = new HashSet<Role>(0);
        roles.add(role);
        user.setRoles(roles);
        userService.createUser(user);
        if (signMe != null && signMe.length() > 0) {
            userService.doAutoLogin(user.getLogin());
        }

        try{
            sendMails.sendMail(user.getEmail(),
                    "User Created",
                    "You create user: "+user.getLogin()+"; with password:"+user.getPassword());
        }
        catch (MailException exp){
            /*NOP This type of exception is already logged*/
        }
            return "redirect:/";
    }

    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    private void loginUser(User user, HttpServletRequest request) {

        String username = user.getLogin();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        request.getSession();

        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

    }

}
