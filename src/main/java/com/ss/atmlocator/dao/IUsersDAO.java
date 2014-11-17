package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.User;

/**
 * Created by Vasyl Danylyuk on 17.11.2014.
 */
public interface IUsersDAO {
    User getUserByName(String name);
}
