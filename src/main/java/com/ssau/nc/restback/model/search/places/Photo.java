package com.ssau.nc.restback.model.search.places;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Photo {

    private Integer height;
    private Integer width;
    @JsonProperty("photo_reference")
    private String photoReference;
    @JsonProperty("html_attributions")
    private List<String> htmlAttributions;
}