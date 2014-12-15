package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmParserParamDAO;
import com.ss.atmlocator.entity.AtmParserParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maks on 04.12.2014.
 */
    @Service
   public class ParserParamService {
    @Autowired
    IAtmParserParamDAO parserParamDAO;
    Logger log = Logger.getLogger(ParserParamService.class.getName());

    public void saveChanges(String paramValue, String paramId){

        String [] values = paramValue.split(",");
        String [] parametersId = paramId.split(",");
        AtmParserParam parserParam;
        AtmParserParam tempParserparam;
        for (int i = 0; i < values.length; i++) {

            int id =Integer.valueOf(parametersId[i]);
            parserParam =parserParamDAO.getParserParamsById(id);

            if(values[i].equals(parserParam.getValue())){
                log.info("value is equals "+values[i]+", "+parserParam.getValue());
            }
            else{
                log.info("value is not equals "+values[i]+", "+parserParam.getValue());
                parserParamDAO.updateParserParam(values[i], id);
            }

        }

    }


}
