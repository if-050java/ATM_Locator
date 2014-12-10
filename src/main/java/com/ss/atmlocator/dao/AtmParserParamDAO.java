package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmParserParam;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by maks on 04.12.2014.
 */
 @Repository
public class AtmParserParamDAO implements IAtmParserParamDAO {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public AtmParserParam getParserParamsById(int id) {
        AtmParserParam atmParserParam =entityManager.find(AtmParserParam.class, id);
        return atmParserParam;
    }

    @Override
    public void updateParserParam(AtmParserParam parserParam) {
        entityManager.merge(parserParam);

    }

    @Override
    @Transactional
    public void updateParserParam(String value, int id) {
        Query qyery = entityManager.createNativeQuery("UPDATE  parser_params SET value =:change where Id=:id");
        qyery.setParameter("change", value);
        qyery.setParameter("id", id);
        qyery.executeUpdate();

    }

}
