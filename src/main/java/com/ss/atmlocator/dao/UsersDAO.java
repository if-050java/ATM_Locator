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
    @Transactional
    public void deleteUser(int id) {
        User deletedUser = entityManager.find(User.class, id);
        entityManager.remove(deletedUser);
    }


    @Override
    @Transactional
    public void updateUser(User user) {
        User persistedUser = entityManager.find(User.class, user.getId());

        if(user.getLogin() != null)
            persistedUser.setLogin(user.getLogin());
        if(user.getAvatar() != null)
            persistedUser.setAvatar(user.getAvatar());
        if(user.getEmail() != null)
            persistedUser.setEmail(user.getEmail());
        if(user.getPassword() != null)
            persistedUser.setPassword(user.getPassword());
        if(user.getRoles() != null)
            persistedUser.setRoles(user.getRoles());
        if(user.getAtmComments() != null)
            persistedUser.setAtmComments(user.getAtmComments());
        if(user.getAtmFavorites() != null)
            persistedUser.setAtmFavorites(user.getAtmFavorites());

        //entityManager.merge(user);
        entityManager.flush();
    }


}
