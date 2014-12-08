package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.ErrorMessage;
import com.ss.atmlocator.utils.jQueryAutoCompleteResponse;
import com.ss.atmlocator.validator.ImageValidator;
import com.ss.atmlocator.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("users")
public class usersController {

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("uservalidator")
    Validator validationService;

    @InitBinder("image")
    protected void initUser1Binder(WebDataBinder binder) {
        binder.setValidator(new ImageValidator());
    }

    @InitBinder("user")
    protected void initUser2Binder(WebDataBinder binder) {
        binder.setValidator(validationService);
    }

    @RequestMapping(value = "/{value}", method = RequestMethod.GET)
    public ResponseEntity<User> findUser(@PathVariable("value") String value) {
        try {
            value = value.replace('*', '.');
            return new ResponseEntity<User>(userService.getUserByName(value), HttpStatus.OK);
        } catch (PersistenceException pe) {
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
        //id of current logged user
        int currentLoggedUserId = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        //Check user want to remove himself
        if (id == currentLoggedUserId) {
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        }
        ;
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
            @Validated @RequestBody User updatedUser,
            @RequestParam(value = "generatePassword", required = false, defaultValue = "false") boolean genPassword,
            Principal principal) {
        updatedUser.setId(id);
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
        } catch (PersistenceException | MessagingException | MailSendException exp ) {
            //todo logger
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    protected ResponseEntity<List<ErrorMessage>> handleInvalidRequest(MethodArgumentNotValidException  e) {
        List<ErrorMessage> errorMessages = new ArrayList<>();
        for(FieldError fieldError : e.getBindingResult().getFieldErrors()){
            errorMessages.add(new ErrorMessage(fieldError.getField(), fieldError.getCode()));
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.NOT_ACCEPTABLE);
    }
}