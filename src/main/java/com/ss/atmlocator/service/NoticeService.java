package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.NoticeDAO;
import com.ss.atmlocator.entity.Notice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Olavin on 22.12.2014.
 */
@Service
public class NoticeService {
    private final static Logger logger = LoggerFactory.getLogger(NoticeService.class);

    @Autowired
    private NoticeDAO noticeDAO;

    public List<Notice> getNoticesList(Timestamp newTime) {
        return noticeDAO.getNoticesList(newTime);
    }

    public List<Notice> getNoticesList() {
        return noticeDAO.getNoticesList();
    }

    private void addNotice(Notice.Type type, String message){
        Notice notice = new Notice();
        notice.setMessage(message);
        notice.setType(type);
        noticeDAO.saveNotice(notice);
    }

    public void info(String message){
        addNotice(Notice.Type.INFO, message);
    }

    public void warn(String message){
        addNotice(Notice.Type.WARN, message);
    }

    public void error(String message){
        addNotice(Notice.Type.ERROR, message);
    }

    public void fatal(String message){
        addNotice(Notice.Type.FATAL, message);
    }

}
