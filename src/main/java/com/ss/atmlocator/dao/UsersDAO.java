package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Vasyl Danylyuk on 16.11.2014.
 */
@Repository("usersDao")
public class UsersDAO implements IUsersDAO{

    @PersistenceContext
    private EntityManager em;


    @Override
    public User getUserByName(String name) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User AS u WHERE u.login=:name",User.class);
        query.setParameter("name", name);
        return query.getSingleResult();

    }


}
