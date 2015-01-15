package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.LogsDAO;
import com.ss.atmlocator.entity.DataTableCriteria;
import com.ss.atmlocator.entity.DataTableResponse;
import com.ss.atmlocator.entity.Logs;
import com.ss.atmlocator.entity.NoticesTable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by d18-antoshkiv on 22.12.2014.
 */
@Service
public class LogsService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogsService.class);
    @Autowired
    private LogsDAO logsDAO;

    public DataTableResponse getLogList(final DataTableCriteria criteria) {
        int start = criteria.getStart();
        int length = criteria.getLength();
        int orderColumn = Integer.parseInt(criteria.getOrder().get(0).get(DataTableCriteria.OrderCriteria.column));
        String orderDirect = criteria.getOrder().get(0).get(DataTableCriteria.OrderCriteria.dir);
        String filter = "%" + criteria.getSearch().get(DataTableCriteria.SearchCriteria.value) + "%";
        LOGGER.debug(String.format("POST: Notices list, offset %d, count %d, order %s %s, filter {%s}",
                start, length, orderColumn, orderDirect, filter));

        NoticesTable table = new NoticesTable();

        table.setData(logsDAO.getLogList(start, length, buildOrderExpression(orderColumn, orderDirect), filter));

        table.setDraw(criteria.getDraw());
        table.setRecordsTotal(logsDAO.getLogsCount());

        long filteredCount = filter.isEmpty() ? table.getRecordsTotal() : logsDAO.getLogsFilteredCount(filter);
        table.setRecordsFiltered(filteredCount);

        return table;

    }

    /**
     * Column names in Logs entity class corresponding to columns order at Web-page:
     */
    private static final String[] FIELD_NAMES = {"dated", "level", "logger", "message"};

    /**
     *
     * @param column Column numbers at Web-page: 0:Time, 1:Type, 2:Source, 3:Message
     * @param order can be 'asc' or 'desc'
     * @return SQL "ORDER BY {fieldName} {order}" expression
     */
    private String buildOrderExpression(final int column, final String order) {
        if (column >= 0 && column < FIELD_NAMES.length && !FIELD_NAMES[column].isEmpty()) {
            return " ORDER BY " + FIELD_NAMES[column] + " " + order;
        } else {
            return "";
        }
    }

}
