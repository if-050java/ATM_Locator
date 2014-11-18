package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Vasyl Danylyuk on 16.11.2014.
 */
public class UsersDAO implements IUsersDAO{

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public User getUserByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(com.ss.atmlocator.entity.User.class);
        criteria.add(Restrictions.like("login", name));
        List usersList = criteria.list();
        if(! usersList.isEmpty()){
            return (User)usersList.get(0);
        } else{
            throw new HibernateException("NoSuchRecords");
        }
    }


}
