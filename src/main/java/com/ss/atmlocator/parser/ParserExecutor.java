package com.ss.atmlocator.parser;

import com.ss.atmlocator.service.IDBParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maks on 15.12.2014.
 */
@Service
public abstract class ParserExecutor {
    @Autowired
    public IDBParserService parserService;

    public abstract void execute();
}
