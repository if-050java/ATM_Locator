package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface IUsersDAO {
    /* Select User from DB by Login*/
    User getUserByName(String name);
    /* Select user from DB by id */
    User getUserById(int id);
    /* Persist updated user information in DB*/
    void updateUser(User user);
    /* Select User from DB by email address */
    User getUserByEmail(String email);
    /* Permanently delete user from DB */
    void deleteUser(int id);
    /* Get default user role */
    Role getDefaultUserRole();
    /* Persist new user in DB */
    void createUser(User user);
    /* Verify existing of login in DB */
    boolean checkExistLoginName(String login);
    boolean checkExistLoginName(User user);
    /* Verify existing of email address in DB */
    boolean checkExistEmail(String email);
    boolean checkExistEmail(User user);
    /* Get user names and emails*/
    List<String> getNames (String partial);
    public void writeLoginTime(String userName);
    public void updateAvatar(int user_id, String avatar);
}
