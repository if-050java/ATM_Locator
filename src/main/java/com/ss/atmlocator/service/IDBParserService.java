package com.ss.atmlocator.service;

import com.ss.atmlocator.entity.AtmOffice;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IDBParserService {
   // public void update(List<AtmOffice> atms);
    public List<AtmOffice> update(List<AtmOffice> atms, int bankId);
}
