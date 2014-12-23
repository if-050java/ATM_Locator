package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmComment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


public interface ICommentsDAO {
    AtmComment getComment(int id);
    void addComment(AtmComment comment);
    void deleteComment(int id);
    List<AtmComment> getComments(int atmId);
}
