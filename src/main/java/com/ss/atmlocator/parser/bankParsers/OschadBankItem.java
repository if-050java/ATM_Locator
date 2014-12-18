package com.ss.atmlocator.parser.bankParsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Olavin on 19.12.2014.
 */
public class OschadBankItem {
    private final static Logger logger = LoggerFactory.getLogger(OschadBankItem.class);
    //private String region;
    private String address;
    private String viddil;


    OschadBankItem(){}

    OschadBankItem(String address, String viddil){
        //this.regNum = regNum;
        setAddress(address);
        setViddil(viddil);
    }

    public String getAddress() {
        return address;
    }

    public String getViddil() {
        return viddil;
    }

    public void setAddress(String address) {
        this.address = address.replaceFirst("Ів\\.-.*Франківськ", "Івано-Франківськ").trim();
    }

    public void setViddil(String viddil) {
        Pattern p = Pattern.compile("(\\d{5})\\s*/+0*(\\d+)"); //TODO replace with parameter
        Matcher m = p.matcher(viddil);
        if (m.find()){
            this.viddil = m.group(1)+"/"+m.group(2);
        } else {
            this.viddil = null;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OschadBankItem that = (OschadBankItem) o;

        if (viddil != null && viddil.equals(that.viddil) ) {
            return true;
        }

        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return (viddil != null ? viddil.hashCode() : address.hashCode());
    }

    @Override
    public String toString() {
        return "OschadBankItem{" +
                "viddil='" + viddil + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
