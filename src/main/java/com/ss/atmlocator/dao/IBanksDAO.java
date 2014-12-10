package com.ss.atmlocator.dao;

import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maks on 30.11.2014.
 */
@Repository
public interface IBanksDAO {
    public List<Bank> getBanksList();
    public List<Bank> getBanksByNetworkId(int network_id);
    public Bank newBank();
    public Bank getBank(int id);
    public Bank saveBank(Bank bank);
    public boolean deleteBank(int bank_id);
    public void saveAllBankNBU(List<Bank> banks);
    public void saveAllBanksUbank(List<Bank> banks);
}
