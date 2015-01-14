package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmComment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ICommentsDAO {
    AtmComment getComment(int id);
   // void addComment(AtmComment comment);

    @Transactional
    void addComment(AtmComment comment, int atmId);

    void deleteComment(int id);
    List<AtmComment> getComments(int atmId);
}
