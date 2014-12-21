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

    private final static String LOCALITY_PATTERN = "(м\\.|с\\.|смт)\\s+(.+?),";

    private String address;
    private String viddil;

    OschadBankItem(String address, String viddil){
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
        this.address = address;
    }

    public void setViddil(String viddil) {
        this.viddil = viddil;
    }

    private String getLocality(){
        //TODO replace with parameter
        Pattern p = Pattern.compile(LOCALITY_PATTERN);
        Matcher m = p.matcher(address);
        return m.find() ? m.group(2) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OschadBankItem that = (OschadBankItem) o;

        if (viddil != null && viddil.equals(that.viddil)) {
            if(getLocality().equals(that.getLocality()))
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
