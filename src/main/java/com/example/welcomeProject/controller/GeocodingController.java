package com.example.welcomeProject.controller;
import com.example.welcomeProject.service.GeocodingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GeocodingController {

   private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/coordinates")
    public Map<String, Object> getCity(@RequestParam String city) {
        return geocodingService.getCity(city);
    }




}