package com.ss.atmlocator.parser.parserNBU;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maks on 19.11.2014.
 */
public class main3 {
    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            if(i==5){
                continue;
            }
            System.out.println(i);
        }
        List<com.ss.atmlocator.entity.Bank> bankList = null;
        Map<String, String> par = new HashMap<String, String>();
        par.put("url", "http://www.bank.gov.ua/control/bankdict/banks?type=369&sort=name&cPage=0&startIndx=1");
        par.put("NAMEXPATH" , "table.col_title_t>tbody>tr:gt(0)>td:eq(0)>a");
        par.put("MFOXPATH" , "table.col_title_t>tbody>tr:gt(0)>td:eq(2)");

//        System.out.println(getName("Gfn \"aaaadfdf\"asdf "));
        NbuParser parser = new NbuParser();
        parser.setParameter(par);

        bankList = parser.parse();
        for(com.ss.atmlocator.entity.Bank bank: bankList){
            System.out.println(bank);
        }
        bankList.size();
    }
//    public String getName(String str){
//        String res=null;
//        Pattern pattern= Pattern.compile("\"(.+)\"");
//        Matcher matcher = pattern.matcher(str);
//        while(matcher.find()){
//            res=matcher.group();
//        }
////        System.out.println(str.matches("\"(.+)/"));
//        return res;
//    }
}

