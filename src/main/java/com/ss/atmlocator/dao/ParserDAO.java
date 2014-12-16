package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmParser;
import com.ss.atmlocator.entity.AtmParserParam;
import com.ss.atmlocator.entity.Bank;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maks on 02.12.2014.
 */
@Repository
public class ParserDAO implements IParserDAO {
    @Autowired
    IBanksDAO banksDAO;
    @PersistenceContext
    private EntityManager entityManager;
    Logger log = Logger.getLogger(ParserDAO.class.getName());


    @Override
    @Transactional
    public List<AtmParser> getParsersByBankId(int id) {
        Bank bank = banksDAO.getBank(id);
        List<AtmParser> list = new ArrayList<AtmParser>(bank.getAtmParserSet());
        System.out.println(list.size());
        return list;
    }

    //    @Transactional
    public AtmParser getParserById2(int id) {

        return entityManager.find(AtmParser.class, id);
/*        TypedQuery<AtmParser> query = entityManager.createQuery("SELECT p FROM AtmParser AS p WHERE p.id=:id", AtmParser.class);
        query.setParameter("id", id);
        AtmParser atmParser = query.getSingleResult();
        return atmParser;*/
    }

    @Override
    @Transactional
    public boolean changeState(int id, int state) {
        if (state == 1 || state == 2) {
         /*     AtmParser atmParser = entityManager.find(AtmParser.class, id);
            atmParser.setState(state);

            entityManager.merge(atmParser);
            System.out.println("sdfsdfsadfasdfasfsd   ---  "+atmParser);
            entityManager.refresh(atmParser);
            return true;*/

            try {
                Query query = entityManager.createNativeQuery("UPDATE  parsers SET parsers.state = :state WHERE parsers.Id = :Id");
                query.setParameter("Id", id);
                query.setParameter("state", state);
                query.executeUpdate();

            } catch (Exception exp) {
//                System.out.println(exp.getMessage());
                log.error("Incorrect data or there is no id in DB -->"+exp.getMessage());
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<AtmParserParam> getParserParamsById(int id) {
        AtmParser atmParser = entityManager.find(AtmParser.class, id);
        List<AtmParserParam> parserParamList = new ArrayList<>(atmParser.getParamSet());
        return parserParamList;
    }

}

