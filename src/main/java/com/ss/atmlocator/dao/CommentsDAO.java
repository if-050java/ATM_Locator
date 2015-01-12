package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmComment;
import com.ss.atmlocator.entity.AtmOffice;
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
    public void addComment(AtmComment comment, int atmId) {
        AtmOffice atmOffice = entityManager.find(AtmOffice.class, atmId);
        comment.setAtmOffice(atmOffice);
        atmOffice.getAtmComments().add(comment);
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
        String queryString = "SELECT c FROM AtmComment AS c WHERE c.atmOffice.id=:atmId";
        TypedQuery<AtmComment> query = entityManager.createQuery(queryString, AtmComment.class);
        query.setParameter("atmId", atmId);
        return query.getResultList();
    }


}
