package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.exception.NotValidException;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.jQueryAutoCompleteResponse;
import com.ss.atmlocator.validator.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.List;


@Controller
@RequestMapping("users")
public class usersController {

    @Autowired
    UserService userService;

    @Autowired
    UserProfileValidator validationService;

    @RequestMapping(value = "/{value}", method = RequestMethod.GET)
    public ResponseEntity<User> findUser(@PathVariable("value")String value) {
        try {
            value = value.replace('*','.');
            return new ResponseEntity<User>(userService.getUserByName(value), HttpStatus.OK);
        } catch (PersistenceException pe) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<jQueryAutoCompleteResponse> getUserNames(@RequestParam("query") String query ){
        List<String> list = userService.setNames(query);

        jQueryAutoCompleteResponse nameResponse = new jQueryAutoCompleteResponse();
        nameResponse.setQuery(query);
        nameResponse.setSuggestions(list);

        return new ResponseEntity<jQueryAutoCompleteResponse>(nameResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        //id of current logged user
        int currentLoggedUserId =  userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        //Check user want to remove himself
        if (id == currentLoggedUserId) {
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        };
        try {
            userService.deleteUser(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }catch (PersistenceException pe) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateUser(@PathVariable("id") int id,
                                             @RequestBody User updatedUser,
                                             @RequestParam(value = "generatePassword", required = false, defaultValue = "false") boolean genPassword){
        updatedUser.setId(id);
        if( userService.isNotModified(updatedUser)){
            return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
        }
        try {
            //id of user who is logged
            String currentLoggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            int currentLoggedUserId =  userService.getUserByName(currentLoggedUserName).getId();
            //try to update user in database
            userService.editUser(updatedUser, genPassword);
            //login if change yourself
            if (updatedUser.getId() == currentLoggedUserId) {
                userService.doAutoLogin(updatedUser.getLogin());
            };
            return new ResponseEntity<String>(HttpStatus.OK);
        }catch (PersistenceException pe){
            return new ResponseEntity<String>("Error data storage",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(MailException me){
            return new ResponseEntity<String>("Error sending e-mail",HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotValidException e) {
            return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
