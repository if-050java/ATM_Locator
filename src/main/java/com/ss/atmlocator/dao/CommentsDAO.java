package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmComment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Vasyl Danylyuk on 17.12.2014.
 */
@Repository
public class CommentsDAO implements IComentsDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addComment(AtmComment comment) {
        entityManager.persist(comment);
    }

    @Override
    public void deleteComment(AtmComment comment) {

    }

    @Override
    public List<AtmComment> getComentsByAtmId(int atmId) {
        TypedQuery<AtmComment> query = entityManager.createQuery("SELECT c FROM AtmComment AS c WHERE c.atmOffice.id=:atmId", AtmComment.class);
        query.setParameter("atmId", atmId);
        return query.getResultList();
    }
}
