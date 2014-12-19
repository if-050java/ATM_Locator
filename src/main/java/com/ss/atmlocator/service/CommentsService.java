package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmsDAO;
import com.ss.atmlocator.dao.ICommentsDAO;
import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.AtmComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Vasyl Danylyuk on 17.12.2014.
 */
@Service
public class CommentsService {

    final static Logger logger = LoggerFactory.getLogger(CommentsService.class);

    @Autowired
    IUsersDAO usersDAO;
    @Autowired
    IAtmsDAO atmsDAO;
    @Autowired
    ICommentsDAO commentsDAO;

    public void addComment(String userName, int atmId, String text){
        AtmComment atmComment = new AtmComment();
        try {
            logger.info("Fill comment fields");
            atmComment.setUser(usersDAO.getUserByName(userName));
            atmComment.setAtmOffice(atmsDAO.getAtmById(atmId));
            atmComment.setText(text);
            atmComment.setTimeCreated(new Timestamp(new Date().getTime()));
            logger.info("Try to save comment");
            commentsDAO.addComment(atmComment);
        }catch (PersistenceException pe){
            logger.error(pe.getMessage(), pe);
            throw pe;
        }
    }

}
