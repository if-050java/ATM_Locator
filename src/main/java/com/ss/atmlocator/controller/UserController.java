package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.entity.UserStatus;
import com.ss.atmlocator.exception.NotValidException;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.*;
import com.ss.atmlocator.validator.ImageValidator;
import com.ss.atmlocator.validator.UserProfileValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.WebDataBinder;
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
public class UserController {

    final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;

    @Autowired
    UserProfileValidator userProfileValidator;

    @Autowired
    ImageValidator imageValidator;

    private static final boolean AUTO_GEN_PASSWORD = false;

    @RequestMapping(value = "/profile")
    public String profile(ModelMap model, Principal principal) {
        String userName = principal.getName();
        model.addAttribute(userService.getUserByName(userName));
        model.addAttribute("active", "profile");
        return "profile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<FieldError>> update
            (
                    User updatedUser,
                    @RequestParam(value = "file", required = false) MultipartFile image,
                    BindingResult result,
                    HttpServletRequest request) throws NotValidException {
        System.out.println(updatedUser);
        userProfileValidator.validate(updatedUser, image, result);
        if (!result.hasErrors()) {
            try {
                if (image != null) {
                    UploadFileUtils.save(image, image.getOriginalFilename(), request);
                }
                userService.editUser(updatedUser, AUTO_GEN_PASSWORD);
                userService.doAutoLogin(updatedUser.getLogin());
            } catch (IOException e) {
                logger.error(ExceptionParser.parseExceptions(e));
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<List<FieldError>>(result.getFieldErrors(), HttpStatus.OK);
    }
}
