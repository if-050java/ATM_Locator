package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.parser.ParserExecutor;

public class UkrSybBankExecutor extends ParserExecutor{
    @Override
    protected void setParser() {
        parser = new UkrSybBankParser();
    }
}
