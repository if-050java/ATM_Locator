package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.User;

public interface IUsersDAO {
    User getUserByName(String name);
    void editUser(User user);

}
