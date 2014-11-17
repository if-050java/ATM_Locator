package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.dao.UsersDAO;
import com.ss.atmlocator.entity.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Vasyl Danylyuk on 14.11.2014.
 */

@Controller
public class AdminUsersController {

    IUsersDAO usersDAO = new UsersDAO();

    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    public @ResponseBody User findUser(HttpServletRequest request){
        String findBy = request.getParameter("findBy");
        String findValue = request.getParameter("findValue");
        if(findBy.equals("name")){
            System.out.println("ВИбрано імя");
        }else{
            System.out.println("вибрано імеіл");
        }
        return new User();
    }



    @RequestMapping(value = "/adminUsers", method = RequestMethod.GET)
    public String adminUsers(){
        return "adminUsers";
    }
}
