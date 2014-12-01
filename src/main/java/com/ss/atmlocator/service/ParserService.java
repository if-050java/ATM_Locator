package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.parser.Parser;
import com.ss.atmlocator.parser.parserNBU.NbuParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maks on 30.11.2014.
 */
@Service
public class ParserService {
    @Autowired
    IBanksDAO banksDAO;

    public void updateAllBanks(){
        Parser parser =new NbuParser();
        Map<String, String> par = new HashMap<String, String>();
        par.put("url", "http://www.bank.gov.ua/control/bankdict/banks?type=369&sort=name&cPage=0&startIndx=1");
        par.put("NAMEXPATH" , "table.col_title_t>tbody>tr:gt(0)>td:eq(0)>a");
        par.put("MFOXPATH" , "table.col_title_t>tbody>tr:gt(0)>td:eq(2)");
        parser.setParametr(par);
        List<Bank> bankList = parser.parce();
        banksDAO.saveAllBankNBU(bankList);
    }
}
