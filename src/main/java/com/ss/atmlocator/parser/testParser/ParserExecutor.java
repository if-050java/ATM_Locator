package com.ss.atmlocator.parser.testParser;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.IParser;
import com.ss.atmlocator.service.IDBParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by maks on 15.12.2014.
 */
@Service
public abstract class ParserExecutor {
    @Autowired
    public IDBParserService parserService;


    public IParser parser;

    public  void execute() throws IOException{
        setParser();
        Map<String,String> parameters=getParametrs();
        parser.setParameter(parameters);
        int bankId = Integer.valueOf(parameters.get("bankId"));
        List<AtmOffice> atms = parser.parse();
        parserService.update(atms, bankId);
    }

    protected  abstract void setParser() ;// Set there  implementation of IParser


    protected abstract Map<String, String> getParametrs();//



}
