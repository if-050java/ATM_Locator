package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Vasyl Danylyuk on 14.11.2014.
 */

@Controller
public class AdminUsersController {

    //Клас для надсилання відповіді про успішність/неуспішність операції
    class AJAXResponse {
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
    public
    @ResponseBody
    User findUser(HttpServletRequest request) {
        String findBy = request.getParameter("findBy");
        String findValue = request.getParameter("findValue");
        User response = null;
        try {
            if (findBy.equals("name")) {
                response = usersDAO.getUserByName(findValue);
            } else {
                response = usersDAO.getUserByEmail(findValue);
            }
        } catch (NoResultException NRE) {
            response = null;
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/adminUsers", method = RequestMethod.GET)
    public String adminUsers() {
        return "adminUsers";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public
    @ResponseBody
    AJAXResponse deleteUser(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        AJAXResponse response = new AJAXResponse();
        try {
            usersDAO.deleteUser(id);
            response.setResult("SUCCESS");
        } catch (Exception HE) {
            response.setResult("ERROR");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public
    @ResponseBody
    AJAXResponse updateUser(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String newLogin = request.getParameter("login");
        String newEmail = request.getParameter("email");
        String newPassword = request.getParameter("password");
        int enabled = Integer.parseInt(request.getParameter("enabled"));

        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setLogin(newLogin);
        updatedUser.setEmail(newEmail);
        updatedUser.setPassword(newPassword);
        updatedUser.setEnabled(enabled);

        AJAXResponse response = new AJAXResponse();
        try {
            usersDAO.updateUser(id, updatedUser);
            response.setResult("SUCCESS");
        } catch (Exception HE) {
            response.setResult("ERROR");
        } finally {
            return response;
        }
    }
}
