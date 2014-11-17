package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by Vasyl Danylyuk on 16.11.2014.
 */
public class UsersDAO implements IUsersDAO{

    private  static Session hibernateSession = HibernateUtil.getSessionFactory().openSession();


    @Override
    public User getUserByName(String name) {
        Criteria criteria = hibernateSession.createCriteria(com.ss.atmlocator.entity.User.class);
        criteria.add(Restrictions.like("login", name));
        List usersList = criteria.list();
        if(! usersList.isEmpty()){
            return (User)usersList.get(0);
        } else{
            throw new HibernateException("NoSuchRecords");
        }
    }
}
