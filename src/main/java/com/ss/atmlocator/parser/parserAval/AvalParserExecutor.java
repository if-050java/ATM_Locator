package com.ss.atmlocator.parser.parserAval;

import com.ss.atmlocator.parser.testParser.ParserExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Class parse Aval Bank
 */
@Service
public class AvalParserExecutor extends ParserExecutor {
    @Override
    protected void setParser() {
        parser = new AvalParser();
    }

    @Override
    protected Map<String, String> getParametrs() {
        // Magic method where we take some parameters
        Map<String, String> parameters =new HashMap<>();
        parameters.put("bankUrl", "http://api.finlocator.com/features/?filters=&theme=aval&section=atm&lat=48.922633&lon=24.71111700000006&lang=ua&filters_atm=&filters_branch=&filters_all=,&_=1418811510013");
        parameters.put("officeUrl", "http://api.finlocator.com/features/?filters=&theme=aval&section=branch&city=29819&lat=48.922633&lon=24.71111700000006&lang=ua&filters_atm=&filters_branch=,,&filters_all=,&_=1418814350710");
        parameters.put("bankId", "30");
        return parameters;
    }
}
