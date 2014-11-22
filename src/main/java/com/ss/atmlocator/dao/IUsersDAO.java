package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersDAO {
    User getUserByName(String name);
    User getUserById(int id);
    void updateUser(User user);
    User getUserByEmail(String email);
    void deleteUser(int id);
}
