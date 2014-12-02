package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.*;
import com.ss.atmlocator.validator.ImageValidator;
import com.ss.atmlocator.validator.UserProfileValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;

    @Autowired
    UserProfileValidator userProfileValidator;

    @Autowired
    ImageValidator imageValidator;

    @RequestMapping(value = "/profile")
    public String profile(ModelMap model, Principal principal) {
        String userName = principal.getName();
        model.addAttribute(userService.getUserByName(userName));
        model.addAttribute("active", "profile");
        return "profile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse update(
            @RequestParam int id,
            @RequestParam String login,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar, HttpServletRequest request
    ) {
        OutResponse response = new OutResponse();
        List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();

        User newUser = new User(id, login, email, password, 1);
        newUser.setAvatar(avatar == null ? null : avatar.getOriginalFilename());
        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), User.class.getName());
        if (userService.isNotModified(newUser)) {
            response.setStatus(Constants.INFO);
            return response;
        }
        userProfileValidator.validate(newUser, avatar, errors);
        if (!errors.hasErrors()) {
            try {
                if (avatar != null)
                    UploadFileUtils.save(avatar, avatar.getOriginalFilename(), request);
                userService.editUser(newUser);
                userService.doAutoLogin(newUser.getLogin());
            } catch (IOException e) {
                logger.error(ExceptionParser.parseExceptions(e));
                response.setStatus(Constants.ERROR);
                return response;
            }
            response.setStatus(Constants.SUCCESS);
            return response;
        }
        for (FieldError objectError : errors.getFieldErrors()) {
            errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getCode()));
        }
        response.setStatus(Constants.WARNING);
        response.setErrorMessageList(errorMesages);
        return response;
    }
}
