package com.ssau.nc.restback.model.search;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssau.nc.restback.model.search.places.Place;

import lombok.Data;

@Data
public class SearchPlacesResponse {

    @JsonProperty(value = "html_attributions")
    private List<String> htmlAttributions;

    @JsonProperty(value = "next_page_token")
    private String nextPageToken;

    private List<Place> results;
}
