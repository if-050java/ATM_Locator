package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.UploadFileUtils;
import com.ss.atmlocator.utils.UploadedFile;
import com.ss.atmlocator.utils.jQueryAutoCompleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("users")
public class usersController {

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("uservalidator")
    Validator userValidator;

    @Autowired
    @Qualifier("imagevalidator")
    Validator imageValidator;
    private final String ADMIN_ROLE_NAME = "ADMIN";
    private final Role ADMIN_ROLE = new Role(ADMIN_ROLE_NAME);

    @RequestMapping(value = "/{value}", method = RequestMethod.GET)
    public ResponseEntity<User> findUser(@PathVariable("value") String value) {
        try {
            value = value.replace('*', '.');
            return new ResponseEntity<User>(userService.getUserByName(value), HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<jQueryAutoCompleteResponse> getUserNames(@RequestParam("query") String query) {
        List<String> list = userService.getNames(query);

        jQueryAutoCompleteResponse nameResponse = new jQueryAutoCompleteResponse();
        nameResponse.setQuery(query);
        nameResponse.setSuggestions(list);

        return new ResponseEntity<jQueryAutoCompleteResponse>(nameResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        //Check want to remove admin
        if (userService.getUserById(id).getRoles().contains(ADMIN_ROLE)) {
            return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            userService.deleteUser(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (PersistenceException pe) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<List<FieldError>> updateUser(
            @PathVariable("id") int id,
            @RequestBody User updatedUser,
            @RequestParam(value = "generatePassword", required = false, defaultValue = "false") boolean genPassword,
            Principal principal,
            BindingResult bindingResult) {
        updatedUser.setId(id);
        userValidator.validate(updatedUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            //id of user who is logged
            int currentLoggedUserId = userService.getUserByName(principal.getName()).getId();
            //try to update user in database
            userService.editUser(updatedUser, genPassword);
            //login if change yourself
            if (id == currentLoggedUserId) {
                userService.doAutoLogin(updatedUser.getLogin());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PersistenceException persistExp) {
            //todo logger
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch ( MessagingException | MailSendException mailExp){
            //todo logger
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/avatar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<ObjectError>> updateAvatar(
            @PathVariable int id,
            UploadedFile file,
            BindingResult result,
            HttpServletRequest request) {

        imageValidator.validate(file, result);
        MultipartFile avatar = file.getFile();
        if (!result.hasErrors() && avatar != null) {
            try {
                String filename = id + avatar.getOriginalFilename();
                UploadFileUtils.save(avatar, filename, request);
                userService.updateAvatar(id, filename);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (IOException e) {
                //todo logging
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(result.getAllErrors(),HttpStatus.NOT_ACCEPTABLE);
    }
}