package com.example.welcomeProject.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GeocodingService {

    @Value("${geocode.api.url}")
    private String apiUrl;

    @Value("${geocode.api.key}")
    private String apiKey;


    private final RestTemplate restTemplate;
    @Autowired
    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getCity(String city) {
        String url = String.format("%s?q=%s&limit=1&appid=%s", apiUrl, city, apiKey);
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
        return response != null && !response.isEmpty() ? response.get(0) : null;
    }

    @Cacheable(value = "geocoordinates", key = "#city")
    public List<Double> getCoordinates(String city)
    {
        String url = String.format("%s?q=%s&limit=1&appid=%s", apiUrl, city, apiKey);


        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

        List<Double> coordinates = new ArrayList<>();

        coordinates.add((double)response.get(0).get("lat"));
        coordinates.add((double)response.get(0).get("lon"));

        return coordinates;
    }


}