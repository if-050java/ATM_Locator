package com.ss.atmlocator.parser.bankParsers;


import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Olavin on 19.12.2014.
 */
public class OschadBankItem {
    private final static Logger logger = LoggerFactory.getLogger(OschadBankItem.class);

    private String address;
    @Nullable
    private String viddil;

    OschadBankItem(String address, String viddil){
        setAddress(address);
        setViddil(viddil);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setViddil(String viddil) {
        this.viddil = viddil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OschadBankItem that = (OschadBankItem) o;
        if (viddil != null && viddil.equals(that.viddil)){
            return true;
        } else {
            return address.equals(that.address);
        }
    }

    @Override
    public int hashCode() {
        return (viddil != null ? viddil.hashCode() : address.hashCode());
    }

    @Override
    public String toString() {
        return "{" + "viddil='" + viddil + '\'' + ", address='" + address + '\'' + '}';
    }
}
