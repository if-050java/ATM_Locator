package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.SignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignUpController {

    final static Logger logger = LoggerFactory.getLogger(SignUpController.class);


    private boolean autoLogin;

    @Autowired
    private SignUpService signUpService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "signup";
    }

    @RequestMapping(value = "/registering", method = RequestMethod.POST)
    public String registering(@RequestParam(value = "inputLogin",required = false) String login,
                              @RequestParam("inputEmail") String email,
                              @RequestParam(value = "inputPassword",required = false) String password,
                              @RequestParam(value = "signMe", required = false) String signMe,
                              Model model,HttpServletRequest request) {

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);

        logger.info("POST parametr login: " +"{"+login+"}");
        logger.info("POST parametr password: " +"{"+password+"}");
        logger.info("POST parametr email: " +"{"+email+"}");

        if (signMe != null && signMe.length() > 0){ autoLogin = true; }

        MapBindingResult errors = signUpService.signUpUser(user,autoLogin);

        if(errors != null && errors.hasErrors()){
            String errorCause = "";
            for(ObjectError err : errors.getAllErrors()){
                errorCause += err.getCode()+"; ";
            }
            String errorMessage = "You entered invalid  parameters: "+errorCause;
            model.addAttribute("error", errorMessage);
            return "signup";
        }

        return "redirect:/";
    }
}