package com.ss.atmlocator.parser.testParser;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.ParserExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maks on 15.12.2014.
 */
@Service
public class TestParserExecutor extends ParserExecutor {
    @Autowired
    TestParser parser;
//    @Autowired
//    IDBParserService parserService;
    @Override
    public void execute() {
       /* IParser parser = new TestParser();*/
        // Magic method or class, where we take some parameters
        Map<String , String> par = new HashMap<String, String>();
        par.put("BankId","1");
        parser.setParameter(par);
        List<AtmOffice> offices = parser.parse();

        parserService.update(offices, 1);
    }
}
