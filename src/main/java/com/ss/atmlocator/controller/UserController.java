package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by roman on 19.11.14.
 */
@Controller
@RequestMapping(value = "/user")
@SessionAttributes("user")
public class UserController {

    public static final String RESOURCES_FOLDER = "\\resources\\images\\";

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("jdbcUserService")
    public UserDetailsManager userDetailsManager;

    @RequestMapping(value = "/profile")
    public String profile(ModelMap model, Principal principal) {
        String userName = principal.getName();
        model.addAttribute(userService.getUserByName(userName));
        return "profile";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(User user, @RequestParam(value = "image", required = false) MultipartFile image, HttpServletRequest request, ModelMap model,final RedirectAttributes redirectAttributes) {

        try {
            if (!image.isEmpty()) {
                user.setAvatar(image.getOriginalFilename());
                saveImage(image, request);
            }
            userService.editUser(user);
            doAutoLogin(user.getLogin());
            redirectAttributes.addFlashAttribute("status", "OK");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("status","ERROR");
        }

        return "redirect:profile";
    }

    private void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private void saveImage(MultipartFile image, HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + RESOURCES_FOLDER;
        File file = new File(path + image.getOriginalFilename());
        FileUtils.writeByteArrayToFile(file, image.getBytes());
    }


}
