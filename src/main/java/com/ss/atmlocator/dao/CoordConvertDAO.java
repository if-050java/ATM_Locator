package com.ss.atmlocator.dao;


import com.ss.atmlocator.entity.AtmState;
import com.ss.atmlocator.parser.coordEncoder.Coord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Repository("coordConvertDAO")
public class CoordConvertDAO {

    private final static String emptyList = "Address input list is  null";

    final static Logger logger = LoggerFactory.getLogger(CoordConvertDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getCoordNames() {
        List<String> address;
        String sqlQuery = "SELECT address FROM atm WHERE state = :state LIMIT 200";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("state", AtmState.NO_LOCATION.ordinal());
        try{
            address = query.getResultList();
        }
        catch(RuntimeException exp){
            address=null;
            logger.error(exp.getMessage(), exp);
        }

        return address;
    }

    @Transactional
    public void updateCoord(List<Coord> coords, List<String> error){
        if(coords == null || coords.isEmpty()){
            error.add(emptyList);
            return;
        }
        String sqlQuery = "UPDATE atm SET latitude = :lat, longitude = :lon,  state = :state WHERE address = :address";
        Query query = entityManager.createNativeQuery(sqlQuery);

        for(Coord coord : coords){
            try{

                query.setParameter("lat", coord.getLatitude());
                query.setParameter("lon", coord.getLongitude());
                query.setParameter("address", coord.getAddress());
                query.setParameter("state", coord.getState().ordinal());
                query.executeUpdate();
            }
            catch (RuntimeException exp){
               error.add(exp.getMessage());
               logger.error(exp.getMessage(), exp);
            }

        }
    }

}
