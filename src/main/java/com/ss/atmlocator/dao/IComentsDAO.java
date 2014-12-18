package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmComment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Vasyl Danylyuk on 17.12.2014.
 */
@Repository
public interface IComentsDAO {
    void addComment(AtmComment comment);
    void deleteComment(AtmComment comment);
    List<AtmComment> getComentsByAtmId(int atmId);
}
