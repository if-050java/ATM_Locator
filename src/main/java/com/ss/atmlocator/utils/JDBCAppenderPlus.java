package com.ss.atmlocator.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by d18-antoshkiv on 22.12.2014.
 *
 * Class necessary as wrapper for original log4j JDBCAppender
 * to escape special characters (single quote) in the log event message
 * for proper SQL statement building
 */
public class JDBCAppenderPlus extends JDBCAppender {

    @Override
    protected String getLogStatement(LoggingEvent event) {

        LoggingEvent clone = new LoggingEvent(
                event.fqnOfCategoryClass,
                LogManager.getLogger(event.getLoggerName()),
                event.getLevel(),
                StringEscapeUtils.escapeSql(event.getMessage().toString()),
                event.getThrowableInformation()!=null ? event.getThrowableInformation().getThrowable() : null
        );

        return getLayout().format(clone);
    }

}
