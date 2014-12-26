package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.FeedBackService;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.FeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class cann
 */
@Controller
public class FeedbackController {
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/feedback", method = RequestMethod.PUT)
    public ResponseEntity<Void> putFeedback(@RequestBody FeedBack feedback,
                            Principal principal) {
        System.out.println(feedback);
        User user = userService.getUser(principal.getName());
//        System.out.println(principal.getName()+ "  " +user.getEmail());
        feedBackService.sentFeedbackTuAdminEmail(feedback.getName()+"\n\r"+feedback.getEmail()+"\n\r"+feedback.getFeedback());
        return  new ResponseEntity<Void>(HttpStatus.OK);

    }
    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> postFeedback(
                                            Principal principal){
        Map<String, String> data = new HashMap();
        if (principal==null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(principal.getName());
        String name = user.getName();
        String email = user.getEmail();

        data.put("name",name);
        data.put("email",email);
        System.out.println(principal.getName()+ "  " +user.getEmail());
        return new ResponseEntity<>(data, HttpStatus.OK);




    }
}
