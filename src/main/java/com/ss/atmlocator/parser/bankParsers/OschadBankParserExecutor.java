package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.parser.ParserExecutor;

/**
 * Created by Olavin on 24.12.2014.
 */
public class OschadBankParserExecutor extends ParserExecutor {
    @Override
    protected void setParser() {
        parser = new OschadBankParser();
    }
}
