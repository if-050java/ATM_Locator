package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmOffice;

import java.util.List;

/**
 * Created by Olavin on 10.12.2014.
 */
public interface IAtmsDAO {

    List<AtmOffice> getBankAtms(int bank_id);
    AtmOffice getAtmById(int id);

}
