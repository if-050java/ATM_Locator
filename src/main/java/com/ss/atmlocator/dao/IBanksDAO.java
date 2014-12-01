package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.enums.Bank;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maks on 30.11.2014.
 */
@Repository
public interface IBanksDAO {
    public List<Bank> getBanksList();
    public Bank newBank();
    public Bank getBank(int id);
    public Bank saveBank(Bank bank);
    public void deleteBank(int bank_id);
    public void saveAllBankNBU(List<Bank> banks);
}
