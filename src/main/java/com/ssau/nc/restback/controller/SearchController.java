package com.ssau.nc.restback.controller;

import com.ssau.nc.restback.controller.utility.SearchUtils;
import com.ssau.nc.restback.google.SearchService;
import com.ssau.nc.restback.model.search.SearchPlacesResponse;
import com.ssau.nc.restback.model.search.places.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ssau.nc.restback.controller.utility.ApiInfo.API_PATH;
import static com.ssau.nc.restback.controller.utility.ApiInfo.V1_PATH;
import static com.ssau.nc.restback.controller.utility.ApiInfo.SEARCH_PATH;

@RestController
@RequestMapping(API_PATH + V1_PATH)
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @CrossOrigin
    @PostMapping(SEARCH_PATH)
    public ResponseEntity uploadExtraFiles(@RequestParam(required = false) String query,
                                           @RequestParam(required = false) List<String> types) {
        List<Place> result;
        log.info("Search places for query: " + query);
        SearchPlacesResponse searchPlacesResponse = searchService.findPlacesByTextQuery(query);

        if (types != null) {
            result = SearchUtils.filterPlaces(searchPlacesResponse.getResults(), types);
        } else {
            result = searchPlacesResponse.getResults();
        }
        log.info("Places size: " + result.size());
        return ResponseEntity.ok(result);
    }
}
