package com.ss.atmlocator.parser.parsers;

import com.ss.atmlocator.entity.Bank;

import java.util.Map;
import java.util.Set;

/**
 * Created by Ivanna Terletska on 20.10.2014.
 */
public interface IParserServise {
    Set<Bank> getItems();

    void setParam(Map<String, String> params);
}
