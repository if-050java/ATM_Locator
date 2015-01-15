package com.ss.atmlocator.parser;

import com.ss.atmlocator.entity.AtmOffice;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public interface IParser {
void setParameter(Map<String, String> parameters);
List<AtmOffice> parse() throws IOException;
}
