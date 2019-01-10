package com.ssau.nc.restback.model.search.places;

import lombok.Data;

@Data
public class Geometry {

    private Coordinates location;
    private Viewport viewport;
}