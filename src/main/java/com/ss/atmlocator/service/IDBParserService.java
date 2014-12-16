package com.ss.atmlocator.service;

import com.ss.atmlocator.entity.AtmOffice;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by maks on 13.12.2014.
 */
@Service
public interface IDBParserService {
    public void update(List<AtmOffice> atms);
    public void update(List<AtmOffice> atms, int bankId);
}
