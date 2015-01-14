package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.AtmComment;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.service.ATMService;
import com.ss.atmlocator.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.security.Principal;
import java.util.List;

/**
 * Created by Vasyl Danylyuk on 17.12.2014.
 */
@Controller
@RequestMapping(value = "/atms")
public class AtmController {

    @Autowired
    private CommentsService commentsService;
    @Autowired
    private ATMService atmService;

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public ResponseEntity<List<AtmComment>> getComments(@PathVariable(value = "id") int atmId) {
        try {
            AtmOffice atmOffice = atmService.getAtmById(atmId);
            List<AtmComment> comments = atmOffice.getAtmComments();
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (NoResultException nre) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.PUT)
    public ResponseEntity<Void> addComment(@PathVariable(value = "id") int atmId,
                                           @RequestBody String comment,
                                           Principal principal) {
        try {
            commentsService.addComment(principal.getName(), atmId, comment);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoResultException nre) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (PersistenceException pe) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/comments/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "id") int atmId,
                                              @PathVariable(value = "commentId") int commentId,
                                              Principal principal) {
        try {
            AtmComment comment = commentsService.getComment(commentId);
            if (! comment.getUser().getLogin().equals(principal.getName())) {
                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
            }
            commentsService.deleteComment(commentId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoResultException nre) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (PersistenceException pe) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
