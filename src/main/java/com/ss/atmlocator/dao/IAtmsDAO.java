package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.AtmOffice;

import java.util.List;

/**
 * Created by Olavin on 10.12.2014.
 */
public interface IAtmsDAO {

    public List<AtmOffice> getBankAtms(Integer network_id, Integer bank_id, boolean showAtms, boolean showOffices);

    AtmOffice getAtmById(int id);

    public List<AtmOffice> getBankAtms(int bank_id);

    void persist(AtmOffice tempAtm);

    void update(List<AtmOffice> atmExistList);

    void persist(List<AtmOffice> atmNewList);
}
