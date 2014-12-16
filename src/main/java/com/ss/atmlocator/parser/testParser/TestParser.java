package com.ss.atmlocator.parser.testParser;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.parser.IParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by maks on 14.12.2014.
 */
@Service
public class TestParser implements IParser {
    int bankId;
    @Override
    public void setParameter(Map<String, String> parameters) {
        bankId = Integer.valueOf(parameters.get("BankId"));
    }

    @Override
    public List<AtmOffice> parse() {
        List<AtmOffice> result= new ArrayList<>();
        for (int i = 0; i <7 ; i++) {
            AtmOffice atm =new AtmOffice();
            atm.setAddress("вул. Громадська "+i*5);
            atm.setType(AtmOffice.AtmType.IS_ATM_OFFICE);

            result.add(atm);
        }

        return result;
    }
}
