package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EntityManager entityManager;

    @Override
    public User getUserByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User AS u WHERE u.login=:name",User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }


    @Override
    public User getUserByEmail(String email) {
      /*  Criteria criteria = sessionFactory.openSession().createCriteria(com.ss.atmlocator.entity.User.class);
        criteria.add(Restrictions.like("email", email));
        List usersList = criteria.list();
        if(! usersList.isEmpty()){
            return (User)usersList.get(0);
        } else{
            throw new HibernateException("NoSuchRecords");
        } */
        return null;
    }

    @Override
    public void deleteUser(String name) {
      /*  try {
            Session session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(com.ss.atmlocator.entity.User.class);
            criteria.add(Restrictions.like("login", name));
            List usersList = criteria.list();
            User user;
            if(! usersList.isEmpty()){
               user = (User)usersList.get(0);
            } else{
                throw new HibernateException("NoSuchRecords");
            }
            session.delete(user);
            session.flush();
        }catch (HibernateException HE){
            throw new HibernateException("Exception in deleting");
        } */
        
    }


}
