package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.UploadFileUtils;
import com.ss.atmlocator.validator.UserProfileValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Created by roman on 19.11.14.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;

    @Autowired
    UserProfileValidator userProfileValidator;


    @RequestMapping(value = "/profile")
    public String profile(ModelMap model, Principal principal) {
        String userName = principal.getName();
        model.addAttribute(userService.getUserByName(userName));
        model.addAttribute("active", "profile");
        return "profile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<FieldError>> update(
            User updatedUser,
            BindingResult result,
            HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile image) {
        userProfileValidator.validate(updatedUser, image, result);
        if (!result.hasErrors()) {
            try {
                if (image != null) {
                    UploadFileUtils.save(image, image.getOriginalFilename(), request);
                }
                userService.editProfile(updatedUser);
                userService.doAutoLogin(updatedUser.getLogin());
                return new ResponseEntity(HttpStatus.OK);
            } catch (IOException | IllegalAccessException e) {
                logger.error(e.getMessage(), e);
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NOT_ACCEPTABLE);
    }
}
