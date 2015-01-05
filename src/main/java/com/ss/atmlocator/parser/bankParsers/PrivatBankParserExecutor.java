package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.parser.ParserExecutor;

public class PrivatBankParserExecutor extends ParserExecutor {
    @Override
    protected void setParser() {
        parser = new PrivatBankParser();
    }
}
