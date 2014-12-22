package com.ss.atmlocator.parser.scheduler;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import org.quartz.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


@DisallowConcurrentExecution
@Configurable
public class JobB implements Job {
    final static Logger logger = LoggerFactory.getLogger(SchcedService.class);

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private UserService userService;
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
        Date date = new Date();


        JobDataMap data = context.getJobDetail().getJobDataMap();
        String message = data.getString("message");
        System.out.println("JobB is running (" +dateFormat.format(date)+")" + ";"+message);
        User user = userService.getUserByName("admin");
        logger.info(user.getAvatar());
    }

   /* -javaagent:"E:\LOCAL_REPO\org\aspectj\aspectjweaver\1.8.4\aspectjweaver-1.8.4.jar"*/
}