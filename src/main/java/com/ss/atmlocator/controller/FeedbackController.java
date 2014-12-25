package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.FeedBackService;
import com.ss.atmlocator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maks on 18.12.2014.
 */
@Controller
public class FeedbackController {
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/feedbacka", method = RequestMethod.PUT)
    public ResponseEntity<Void> putFeedback(@RequestBody String feedback,
                            Principal principal){
        System.out.println(feedback);
        User user = userService.getUser(principal.getName());
        System.out.println(principal.getName()+ "  " +user.getEmail());

        return  new ResponseEntity<Void>(HttpStatus.OK);

    }
    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ResponseEntity<List<String>> postFeedback(@RequestBody
                                            Principal principal){
        List<String> data = new ArrayList();
        if(principal==null){
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(principal.getName());
        String name = user.getName();
        String email = user.getEmail();

        data.add(name);
        data.add(email);
        System.out.println(principal.getName()+ "  " +user.getEmail());
        return new ResponseEntity<>(data, HttpStatus.OK);




    }
}
