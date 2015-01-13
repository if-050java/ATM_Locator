package com.ss.atmlocator.parser.bankParsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ss.atmlocator.parser.ParserExecutor;
import org.springframework.stereotype.Service;

/**
 * Class parse Aval Bank
 */
@Service
public class AvalParserExecutor extends ParserExecutor {

    final static Logger logger = LoggerFactory.getLogger(AvalParserExecutor.class);
    @Override
    protected void setParser() {
        parser = new AvalParser();
        logger.trace("[PARSER EXECUTOR] parser set successfully");
    }

}
