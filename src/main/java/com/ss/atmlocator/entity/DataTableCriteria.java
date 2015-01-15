package com.ss.atmlocator.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by Olavin on 12.01.2015.
 */
public class DataTableCriteria {
    private int draw;
    private int start;
    private int length;

    private Map<SearchCriteria, String> search;

    private List<Map<ColumnCriteria, String>> columns;

    private List<Map<OrderCriteria, String>> order;

    public enum SearchCriteria {
        value, // searched string
        regex  // 'true' or 'false'
    }
    public enum OrderCriteria {
        column, // number of ordered column
        dir  // 'asc' or 'desc'
    }
    public enum ColumnCriteria {
        data,
        name,
        searchable,
        orderable,
        searchValue,
        searchRegex
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Map<SearchCriteria, String> getSearch() {
        return search;
    }

    public void setSearch(Map<SearchCriteria, String> search) {
        this.search = search;
    }

    public List<Map<ColumnCriteria, String>> getColumns() {
        return columns;
    }

    public void setColumns(List<Map<ColumnCriteria, String>> columns) {
        this.columns = columns;
    }

    public List<Map<OrderCriteria, String>> getOrder() {
        return order;
    }

    public void setOrder(List<Map<OrderCriteria, String>> order) {
        this.order = order;
    }

}
