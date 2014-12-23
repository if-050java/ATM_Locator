package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Repository
public class UsersDAO implements IUsersDAO {

    private final String DEFAULT_USER_ROLE = "USER";

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Autowired
    IAtmsDAO atmsDAO;

    @Override
    public User getUser(String name) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User AS u WHERE u.login=:name OR u.email=:name", User.class);
        query.setParameter("name", name);
        User user = query.getSingleResult();
        return user;
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }


/*    @Override
    public User getUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User AS u WHERE u.email=:email", User.class);
        query.setParameter("email", email);
        User user = query.getSingleResult();
        return user;

    }*/

    @Override
    @Transactional
    public void deleteUser(int id) {
        User deletedUser = entityManager.find(User.class, id);
        entityManager.remove(deletedUser);
    }


    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public Role getDefaultUserRole() {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role AS r WHERE r.name=:name", Role.class);
        query.setParameter("name", DEFAULT_USER_ROLE);
        Role role = query.getSingleResult();
        return role;
    }

    @Override
    @Transactional
    public void createUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public boolean checkExistLoginName(String login) {
        String sqlQuery = "SELECT COUNT(*) FROM users WHERE login = :login";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("login", login);
        int value =  ((BigInteger) query.getSingleResult()).intValue();
        if (value == 0 ) return false;
        return true;
    }
    @Override
    public boolean checkExistLoginName(User user) {
        String sqlQuery = "SELECT COUNT(*) FROM users WHERE login = :login and id != :id";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("id", user.getId());
        query.setParameter("login", user.getLogin());
        int value =  ((BigInteger) query.getSingleResult()).intValue();
        if (value == 0 ) return false;
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        String sqlQuery = "SELECT COUNT(*) FROM users WHERE email = :email";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("email", email);
        int value =  ((BigInteger) query.getSingleResult()).intValue();
        if (value == 0 ) return false;
        return true;
    }

    @Override
    public boolean checkExistEmail(User user) {
        String sqlQuery = "SELECT COUNT(*) FROM users WHERE email = :email and id != :id";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("id", user.getId());
        query.setParameter("email", user.getEmail());
        int value =  ((BigInteger) query.getSingleResult()).intValue();
        if (value == 0 ) return false;
        return true;
    }

    @Override
    public List<String> getNames(String partial) {
        List<String> result = new ArrayList<String>();
        String sqlQuery = "SELECT login FROM users WHERE login LIKE :partial UNION SELECT email FROM users WHERE email LIKE :partial LIMIT 5";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("partial", "%"+partial+"%");
        result.addAll(query.getResultList());
        return result;
    }

    @Override
    @Transactional
    public void writeLoginTime(String userName){

        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        String sqlQuery = "UPDATE users SET lastLoging = :timestamp WHERE login = :username";
        Query query = entityManager.createNativeQuery(sqlQuery);

        query.setParameter("timestamp", currentTimestamp);
        query.setParameter("username", userName);

        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateAvatar(int user_id, String avatar){
        String sqlQuery = "UPDATE users SET avatar = :avatar WHERE id = :id";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("avatar", avatar);
        query.setParameter("id", user_id);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public Set<AtmOffice> getFavorites(int userId) {
        User user = entityManager.find(User.class, userId);
        entityManager.refresh(user);
        Set<AtmOffice> favorites = user.getAtmFavorites();
        return favorites;
    }

    @Override
    @Transactional
    public void addFavorite(int userId, int atmId){
        User user = getUser(userId);
        Set<AtmOffice> favorites = user.getAtmFavorites();
        favorites.add(atmsDAO.getAtmById(atmId));
        updateUser(user);
    }

    @Override
    @Transactional
    public void deleteFavorite(int userId, int atmId) {
        User user = getUser(userId);
        Set<AtmOffice> favorites = user.getAtmFavorites();
        favorites.remove(atmsDAO.getAtmById(atmId));
        updateUser(user);
    }


}
