package com.ssau.nc.restback.model.search.places;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Place {

    @JsonProperty("formatted_address")
    private String formattedAddress;

    private Geometry geometry;

    private String icon;

    private String id;

    private String name;

    @JsonProperty("opening_hours")
    private Map<String, String> openingHours;

    @JsonProperty
    private List<Photo> photos;

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("plus_code")
    private Map<String, String> plusCode;

    @JsonProperty("price_level")
    private Short priceLevel;

    private String rating;

    private String reference;

    private List<String> types;
}