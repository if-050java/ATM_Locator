package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmParserParam;
import org.springframework.stereotype.Repository;

/**
 * Created by maks on 03.12.2014.
 */
@Repository
public interface IAtmParserParamDAO {
    public AtmParserParam getParserParamsById(int id);
    public void updateParserParam(AtmParserParam parserParam);
    public void updateParserParam(String param, int id);
}
