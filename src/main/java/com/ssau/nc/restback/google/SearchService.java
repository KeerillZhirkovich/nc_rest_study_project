package com.ssau.nc.restback.google;

import com.ssau.nc.restback.model.search.SearchPlacesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.ssau.nc.restback.google.utility.GoogleMapsApiConstants.GOOGLE_MAPS_API_URL;
import static com.ssau.nc.restback.google.utility.GoogleMapsApiConstants.KEY;
import static com.ssau.nc.restback.google.utility.GoogleMapsApiConstants.PLACE;
import static com.ssau.nc.restback.google.utility.GoogleMapsApiConstants.QUERY;
import static com.ssau.nc.restback.google.utility.GoogleMapsApiConstants.TEXT_SEARCH;
import static com.ssau.nc.restback.google.utility.GoogleMapsApiConstants.TYPE_JSON;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.key:key}")
    private String apiKey;


    public SearchPlacesResponse findPlacesByTextQuery(String query) {
        //FIXME сработало - не трогал. В процессе заменить на нормальное построение урла
        String url = GOOGLE_MAPS_API_URL + PLACE + TEXT_SEARCH + TYPE_JSON + '?' + QUERY + '=' + query + '&' + KEY + '=' + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        log.info("Send request to: " + url);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<SearchPlacesResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                SearchPlacesResponse.class);

        return response.getBody();
    }
}
