package com.ss.atmlocator.parser;



import com.ss.atmlocator.entity.Bank;

import java.util.List;
import java.util.Map;

/**
 * Created by maks on 19.11.2014.
 */
public interface IParser {
    public void setParameter(Map<String, String> parameters);
    public List<Bank> parse();
}