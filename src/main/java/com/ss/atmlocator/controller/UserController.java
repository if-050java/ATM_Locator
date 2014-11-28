package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.UploadFileUtils;
import com.ss.atmlocator.utils.ValidateUserFields;
import com.ss.atmlocator.utils.ValidationOutMessage;
import com.ss.atmlocator.validator.ImageValidator;
import com.ss.atmlocator.validator.UserProfileValidator;
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


    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_INFO = "INFO";
    public static final String STATUS_ERROR = "ERROR";
    public static final String NOTHING_TO_UPDATE = "NOTHING";

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

//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public String saveUser(User user, @RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest request, ModelMap model, final RedirectAttributes redirectAttributes) {
//
//        try {
//            if (!image.isEmpty()) {
//                user.setAvatar(image.getOriginalFilename());
//                saveImage(image, request);
//            }
//            userService.editUser(user);
//            doAutoLogin(user.getLogin());
//            redirectAttributes.addFlashAttribute("status", "OK");
//        } catch (IOException e) {
//            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("status", "ERROR");
//        }
//
//        return "redirect:profile";
//    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ValidationOutMessage update(
            @RequestParam int id,
            @RequestParam String login,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar, HttpServletRequest request
    ) {
        ValidationOutMessage response = new ValidationOutMessage();
        List<ValidateUserFields> errorMesages = new ArrayList<ValidateUserFields>();

        User newUser = new User(id, login, email, password, 1);
        newUser.setAvatar(avatar == null ? null : avatar.getOriginalFilename());
        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), User.class.getName());
        userProfileValidator.validate(newUser, avatar, errors);
        if (!errors.hasErrors()) {
            try {
                userService.editUser(newUser);
                if (avatar != null) UploadFileUtils.save(avatar, avatar.getOriginalFilename(), request);
                userService.doAutoLogin(newUser.getLogin());
            } catch (IOException e) {
                e.printStackTrace();
                //TODO logging
                response.setStatus(STATUS_ERROR);
                return response;
            }
            response.setStatus(STATUS_SUCCESS);
            return response;
        }
        for (FieldError objectError : errors.getFieldErrors()) {
            errorMesages.add(new ValidateUserFields(objectError.getField().toLowerCase(), objectError.getCode()));
            if (objectError.getField().equals(NOTHING_TO_UPDATE)) {
                response.setStatus(STATUS_INFO);
                return response;
            }
        }
        response.setStatus(STATUS_ERROR);
        response.setValidateUserFieldsList(errorMesages);
        return response;
    }
}
