package com.ss.atmlocator.parser.coordEncoder;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class Result {

    @JsonProperty("formatted_address")
    private String formattedAddress;

    @JsonProperty("partial_match")
    private boolean partialMatch;

    private Geometry geometry;

    @JsonIgnore
    @JsonProperty("address_components")
    private Object addressComponents;

    @JsonIgnore
    private Object types;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public boolean isPartialMatch() {
        return partialMatch;
    }

    public void setpartialMatch(boolean partialMatch) {
        this.partialMatch = partialMatch;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Object getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(Object addressComponents) {
        this.addressComponents = addressComponents;
    }

    public Object getTypes() {
        return types;
    }

    public void setTypes(Object types) {
        this.types = types;
    }



}