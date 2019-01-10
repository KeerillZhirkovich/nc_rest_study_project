package com.ssau.nc.restback.controller.utility;

import com.ssau.nc.restback.model.search.places.Place;

import java.util.ArrayList;
import java.util.List;

public class SearchUtils {

    public static List<Place> filterPlaces(List<Place> places, List<String> types) {
        List<Place> result = new ArrayList<>();

        for (Place place : places) {
            if (isPlaceContainsRequiredTypes(place, types)) {
                result.add(place);
            }
        }

        return result;
    }

    private static boolean isPlaceContainsRequiredTypes(Place place, List<String> types) {
        for (String type : types) {
            if (place.getTypes().contains(type)) {
                return true;
            }
        }
        return false;
    }
}
