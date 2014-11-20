package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class UsersDAO implements IUsersDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User AS u WHERE u.login=:name", User.class);
        query.setParameter("name", name);
        User user = query.getSingleResult();
        return user;
    }


    @Override
    public User getUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User AS u WHERE u.email=:email", User.class);
        query.setParameter("email", email);
        User user = query.getSingleResult();
        return user;

    }

    @Override
    public void deleteUser(int id) {
        //Creating new EntityManager for deleting(Default can't delete/update anything. Why?)
        EntityManager deleteEntityManager = entityManager.getEntityManagerFactory().createEntityManager();
        //Finding user for deleting by id
        User deleteUser = deleteEntityManager.find(User.class, id);
        //Creating and start deleting transaction
        EntityTransaction deletingTransaction = deleteEntityManager.getTransaction();
        deletingTransaction.begin();
        //Deleting user from DB
        deleteEntityManager.remove(deleteUser);
        deletingTransaction.commit();
    }

    @Override
    public void updateUser(int id, User user) {
        //Creating new EntityManager for updating(Default can't delete/update anything. Why?)
        EntityManager updateEntityManager = entityManager.getEntityManagerFactory().createEntityManager();
        //Finding user for updating by id
        User updatedUser = updateEntityManager.find(User.class, id);
        //Creating and start updating transaction
        EntityTransaction updatingTransaction = updateEntityManager.getTransaction();
        updatingTransaction.begin();
        //Updating fields in persisted user
        updatedUser.setLogin(user.getLogin());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setEnabled(user.getEnabled());
        //Updating data in DB
        updateEntityManager.flush();
        updatingTransaction.commit();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }


}
