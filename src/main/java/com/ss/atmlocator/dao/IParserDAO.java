package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmParser;
import com.ss.atmlocator.entity.AtmParserParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maks on 02.12.2014.
 */
@Repository
public interface IParserDAO {
    public List<AtmParser> getParsersByBankId(int id);
    public AtmParser getParserById2(int id);
    public boolean changeState(int id, int state);
    public List<AtmParserParam> getParserParamsById(int id);
}
