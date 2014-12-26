package com.ss.atmlocator.utils;

import java.util.List;

public class JQueryAutoCompleteResponse {
    private String query;
    private List<String> suggestions;

    public JQueryAutoCompleteResponse(String query, List<String> suggestions) {
        this.query = query;
        this.suggestions = suggestions;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}
