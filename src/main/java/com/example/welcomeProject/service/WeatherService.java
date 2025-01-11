package com.example.welcomeProject.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final GeocodingService geocodingService;

    public WeatherService(RestTemplate restTemplate,GeocodingService geocodingService) {
        this.restTemplate = restTemplate;
        this.geocodingService = geocodingService;
    }
    @Cacheable(value = "weather", key = "#city")
    public Map<String, Object> getCurrentWeather(String city) {

        String lat = String.valueOf(geocodingService.getCoordinates(city).get(0));
        String lon = String.valueOf(geocodingService.getCoordinates(city).get(1));
        String part = "hourly";

        String url = String.format(
                "%s/weather?lat=%s&lon=%s&exclude=%s&appid=%s&units=metric",
                apiUrl, // Base API URL
                lat,    // Latitude
                lon,    // Longitude
                part,   // Parts to exclude (e.g., "minutely,hourly")
                apiKey  // Your API Key
        );

        return restTemplate.getForObject(url, Map.class);
    }
}
