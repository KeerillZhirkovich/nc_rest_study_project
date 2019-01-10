package com.ssau.nc.restback.model.search.places;

import lombok.Data;

@Data
public class Viewport {

    private Coordinates northeast;
    private Coordinates southwest;
}