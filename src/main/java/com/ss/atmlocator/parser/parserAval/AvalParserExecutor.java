package com.ss.atmlocator.parser.parserAval;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ss.atmlocator.parser.testParser.ParserExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Class parse Aval Bank
 */
@Service
public class AvalParserExecutor extends ParserExecutor {



    final static Logger logger = LoggerFactory.getLogger(AvalParserExecutor.class);
    @Override
    protected void setParser() {

        parser = new AvalParser();
        logger.trace("parser set");
    }

    @Override
    protected Map<String, String> getParametrs() {
        // Magic method where we take some parameters
        Map<String, String> parameters = new HashMap<>();
        parameters.put("bankUrl", "http://api.finlocator.com/features/?filters=&theme=aval&section=atm&lat=48.922633&lon=24.71111700000006&lang=ua&filters_atm=&filters_branch=&filters_all=,&_=1418811510013");
        parameters.put("officeUrl", "http://api.finlocator.com/features/?filters=&theme=aval&section=branch&city=29819&lat=48.922633&lon=24.71111700000006&lang=ua&filters_atm=&filters_branch=,,&filters_all=,&_=1418814350710");
        parameters.put("bankId", "30");
        logger.trace("parameters map set");
        return parameters;
    }
}
