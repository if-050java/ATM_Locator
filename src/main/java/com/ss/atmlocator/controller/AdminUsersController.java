package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.dao.UsersDAO;
import com.ss.atmlocator.entity.User;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    //Клас для надсилання відповіді про успішність/неуспішність операції
    class AJAXResponse{
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    @Autowired
    private IUsersDAO usersDAO;

    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    public @ResponseBody User findUser(HttpServletRequest request){
        String findBy = request.getParameter("findBy");
        String findValue = request.getParameter("findValue");
        User responce = null;
        if(findBy.equals("name")){
            responce = usersDAO.getUserByName(findValue);
        }else{
            responce = usersDAO.getUserByEmail(findValue);
        }
        return responce;
    }

    @RequestMapping(value = "/adminUsers", method = RequestMethod.GET)
    public String adminUsers(){
        return "adminUsers";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public  @ResponseBody AJAXResponse deleteUser(HttpServletRequest request){
        String userName = request.getParameter("login");
        AJAXResponse response = new AJAXResponse();
        try {
            usersDAO.deleteUserByName(userName);
            response.setResult("SUCCESS");
        }catch (HibernateException HE){
            response.setResult("ERROR");
        }finally {
            return response;
        }
    }
}
