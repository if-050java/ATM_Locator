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
 * The class is controller for feedback page
 */
@Controller
public class FeedbackController {
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private UserService userService;
    /**
     * This method returns http status 200 and send feedback to admin email.
     * */
    @RequestMapping(value = "/feedback", method = RequestMethod.PUT)
    public ResponseEntity<Void> putFeedback(@RequestBody FeedBack feedback,
                            Principal principal) {
        feedBackService.sentFeedbackTuAdminEmail(feedback.getName()+"\n\r"+feedback.getEmail()+"\n\r"+feedback.getFeedback());
        return  new ResponseEntity<Void>(HttpStatus.OK);

    }
    /**
     * This method returns http status 200 and   information about the user.
     * */
    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> aboutUser(
            Principal principal){
        Map<String, String> data = new HashMap();
        if(principal==null){
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        User user = userService.getUser(principal.getName());
        String name = user.getName();
        String email = user.getEmail();
        data.put("name",name);
        data.put("email",email);
        return new ResponseEntity<>(data, HttpStatus.OK);




    }
}
