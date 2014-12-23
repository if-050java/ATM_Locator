package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping(value = "/favorites")
public class FavoritesController {

    @Autowired
    UserService userService;

    @RequestMapping
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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
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


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
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
