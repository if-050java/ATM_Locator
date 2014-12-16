package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.dao.IParserDAO;
import com.ss.atmlocator.entity.AtmParser;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.parser.IParsers;
import com.ss.atmlocator.parser.parserNBU.NbuParser;
import com.ss.atmlocator.parser.parserUbanks.BankUrlsIParserServer;
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
    @Autowired
    IParserDAO parserDAO;

    public void updateAllBanks(){

        IParsers parser =new NbuParser();
        Map<String, String> par = new HashMap<String, String>();
        par.put("url", "http://www.bank.gov.ua/control/bankdict/banks?type=369&sort=name&cPage=0&startIndx=1");
        par.put("NAMEXPATH" , "table.col_title_t>tbody>tr:gt(0)>td:eq(0)>a");
        par.put("MFOXPATH" , "table.col_title_t>tbody>tr:gt(0)>td:eq(2)");
        par.put("PAGINATORPATH","div.content>table:eq(5)>tbody>tr>td:eq(1)>a");
        parser.setParameter(par);
        List<Bank> bankList = parser.parse();
        banksDAO.saveAllBankNBU(bankList);
    }

    public void updateUbanks(){
        List<Bank> banks;
        BankUrlsIParserServer parseUrl = new BankUrlsIParserServer();
        Map<String, String> map = new HashMap<String, String>();
        map.put("url", "http://ubanks.com.ua/city/ivano-frankivska.php");
        parseUrl.setParameter(map);
        banks=parseUrl.parse();
        banksDAO.saveAllBanksUbank(banks);
    }

    public boolean changeState(int id, int state){

        return parserDAO.changeState( id, state);
    }
    public List<AtmParser> getParserByBankId(int id){

        return  parserDAO.getParsersByBankId(id);
    }
}
