package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmComment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


public interface ICommentsDAO {
    void addComment(AtmComment comment);
    void deleteComment(AtmComment comment);
    List<AtmComment> getCommentsByAtmId(int atmId);
}
