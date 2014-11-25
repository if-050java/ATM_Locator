package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.service.UserValidatorService;
import com.ss.atmlocator.utils.ErrorMessage;
import com.ss.atmlocator.utils.ValidationResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roman on 19.11.14.
 */
@Controller
@RequestMapping(value = "/user")
@SessionAttributes("user")
public class UserController {

    public static final String RESOURCES_FOLDER = "\\resources\\images\\";

    @Autowired
    UserService userService;

    @Autowired
    UserValidatorService validationService;

    @Autowired
    @Qualifier("jdbcUserService")
    public UserDetailsManager userDetailsManager;

    @RequestMapping(value = "/profile")
    public String profile(ModelMap model, Principal principal) {
        String userName = principal.getName();
        model.addAttribute(userService.getUserByName(userName));
        model.addAttribute("active", "profile");
        return "profile";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(User user, @RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest request, ModelMap model, final RedirectAttributes redirectAttributes) {

        try {
            if (!image.isEmpty()) {
                user.setAvatar(image.getOriginalFilename());
                saveImage(image, request);
            }
            userService.editUser(user);
            doAutoLogin(user.getLogin());
            redirectAttributes.addFlashAttribute("status", "OK");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("status", "ERROR");
        }

        return "redirect:profile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse update(
            @RequestParam int id,
            @RequestParam String login,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ) {
        ValidationResponse response = new ValidationResponse();
        User newUser = new User(id, login, email, password, 1);
        newUser.setAvatar(avatar==null ? null : avatar.getOriginalFilename());

        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), User.class.getName());
        validationService.validate(newUser, errors);
        if (!errors.hasErrors()) {
            response.setStatus("SUCCESS");
            return response;
        }
        List<FieldError> allErrors = errors.getFieldErrors();
        List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
        for (FieldError objectError : allErrors) {
            errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getCode()));
            if(objectError.getField().equals("nothing")){
                response.setStatus("INFO");
                response.setErrorMessageList(errorMesages);
                return response;
            }
        }
        response.setStatus("ERROR");
        response.setErrorMessageList(errorMesages);
        return response;
    }

    private void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private void saveImage(MultipartFile image, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + RESOURCES_FOLDER;
        File file = new File(path + image.getOriginalFilename());
        FileUtils.writeByteArrayToFile(file, image.getBytes());
    }


}
