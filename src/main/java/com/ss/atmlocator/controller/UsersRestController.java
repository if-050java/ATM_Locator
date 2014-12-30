package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.UploadedFile;
import com.ss.atmlocator.utils.JQueryAutoCompleteResponse;
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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

import static com.ss.atmlocator.utils.Constants.USER_AVATAR_PREFIX;


@Controller
@RequestMapping("/users")
public class UsersRestController {

    private static final String ADMIN_ROLE_NAME = "ADMIN";
    private static final Role ADMIN_ROLE = new Role(ADMIN_ROLE_NAME);


    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("uservalidator")
    private Validator userValidator;

    @Autowired
    @Qualifier("imagevalidator")
    private Validator imageValidator;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<JQueryAutoCompleteResponse> getUserNames(@RequestParam("query") String query) {
        List<String> listUsers = userService.getNames(query);
        return new ResponseEntity<>(new JQueryAutoCompleteResponse(query, listUsers), HttpStatus.OK);
    }

    @RequestMapping(value = "/{value}", method = RequestMethod.GET)
    public ResponseEntity<User> findUser(@PathVariable("value") String value) {
        try {
            value = value.replace('*', '.');
            return new ResponseEntity<>(userService.getUser(value), HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        if (userService.getUser(id).getRoles().contains(ADMIN_ROLE)) {//Check want to remove admin
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (PersistenceException pe) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
            User loggedUser = userService.getUser(principal.getName());
            userService.editUser(updatedUser, genPassword);//try to update user in database
            if (id == loggedUser.getId() && updatedUser.getLogin() != null) { //login if change yourself
                userService.doAutoLogin(updatedUser.getLogin());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PersistenceException persistExp) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MessagingException | MailSendException mailExp) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
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
        if (!result.hasErrors()) {
            try {
                String newName = UploadedFile.saveImage(avatar, USER_AVATAR_PREFIX, id, request);
                userService.updateAvatar(id, newName);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (IOException ioe) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(result.getAllErrors(), HttpStatus.NOT_ACCEPTABLE);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Users favorites
    @RequestMapping(value = "/favorites/", method = RequestMethod.GET)
    public ResponseEntity<Set<AtmOffice>> getFavorites(Principal user) {
        try {
            int userId = userService.getUser(user.getName()).getId();
            return new ResponseEntity<>(userService.getFavorites(userId), HttpStatus.OK);
        } catch (NoResultException nre) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (PersistenceException pe) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/favorites/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> addFavorite(@PathVariable int id,
                                            Principal user) {
        try {
            int userId = userService.getUser(user.getName()).getId();
            userService.addFavorite(userId, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PersistenceException pe) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @RequestMapping(value = "/favorites/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delFromFavorites(@PathVariable int id,
                                                 Principal user){
        try {
            int userId = userService.getUser(user.getName()).getId();
            userService.deleteFavorite(userId, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }  catch (PersistenceException pe) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}