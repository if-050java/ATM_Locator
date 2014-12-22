package com.ss.atmlocator.controller;

import com.ss.atmlocator.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by maks on 18.12.2014.
 */
@Controller
public class FeedbackController {
    @Autowired
    private FeedBackService feedBackService;
    @RequestMapping(value = "/feedback", method = RequestMethod.PUT)
    public ResponseEntity<Void> putFeedback(@RequestBody String feedback,
                            Principal principal){
        System.out.println(feedback);
        System.out.println(principal);
        feedBackService.sentFedbackTuAdminEmail(feedback);

        return  new ResponseEntity<Void>(HttpStatus.OK);

    }
}
