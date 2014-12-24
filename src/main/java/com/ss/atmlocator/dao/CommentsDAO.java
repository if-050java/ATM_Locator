package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmComment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentsDAO implements ICommentsDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AtmComment getComment(int id) {
        return entityManager.find(AtmComment.class, id);
    }

    @Override
    @Transactional
    public void addComment(AtmComment comment) {
        entityManager.persist(comment);
    }

    @Override
    @Transactional
    public void deleteComment(int id) {
        AtmComment comment = entityManager.find(AtmComment.class, id);
        comment.getAtmOffice().getAtmComments().remove(comment);
        entityManager.remove(comment);
    }

    @Override
    public List<AtmComment> getComments(int atmId) {
        TypedQuery<AtmComment> query = entityManager.createQuery("SELECT c FROM AtmComment AS c WHERE c.atmOffice.id=:atmId", AtmComment.class);
        query.setParameter("atmId", atmId);
        return query.getResultList();
    }


}
