package com.ss.atmlocator.utils;

import java.util.List;

public class jQueryAutoCompleteResponse {
    private String query;
    private List<String> suggestions;

    public jQueryAutoCompleteResponse(String query, List<String> suggestions) {
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
