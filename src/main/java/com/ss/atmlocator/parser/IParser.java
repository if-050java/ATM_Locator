package com.ss.atmlocator.parser;



import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by maks on 19.11.2014.
 */
public interface IParser {
    void setParameter(Map<String, String> parameters);
    List<Bank> parse();
}
