package com.avinash.scm.springbootcrudapi.controller;

import com.avinash.scm.springbootcrudapi.model.Country;
import com.avinash.scm.springbootcrudapi.service.ICountryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyController {
    private static final Logger LOGGER = LogManager.getLogger(MyController.class);
    @Autowired
    private ICountryService countryService;

    @GetMapping("/countries/{pageNo}/{pageSize}")
    //path variable http://localhost:8080/countries/1/2
    public Map<String, Object> getPaginatedCountries(@PathVariable int pageNo,
                                                     @PathVariable int pageSize) {
        try {
            List<Country> countries;
            Page<Country> pageCountry;
            pageCountry = countryService.findAll(pageNo, pageSize);
            countries = pageCountry.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("countries", countries);
            response.put("currentPage", pageCountry.getNumber());
            response.put("totalItems", pageCountry.getTotalElements());
            response.put("totalPages", pageCountry.getTotalPages());
            response.put("lastPage",pageCountry.stream().findFirst());
            return response;
        } catch (Exception e) {
            LOGGER.error("error : " + e);
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return response;
        }
    }

    @GetMapping("/countries")
    // request param http://localhost:8080/countries?pageNo=2&pageSize=4
    public ResponseEntity<Map<String, Object>> getAllTutorials(
            // @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "3") int pageSize
    ) {
        try {
            List<Country> countries;
            Page<Country> pageCountry;
            pageCountry = countryService.findAll(pageNo, pageSize);
            countries = pageCountry.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("countries", countries);
            response.put("currentPage", pageCountry.getNumber());
            response.put("totalItems", pageCountry.getTotalElements());
            response.put("totalPages", pageCountry.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("error : " + e);
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/findAll")
    public List<Country> findAll(){
        return countryService.findAll();
    }

    @GetMapping("/findByCountryName/{countryName}")
    public List<Country> findByName(@PathVariable String countryName){

        Country country = countryService.getCountryByName(countryName);
        List<Country> countries = countryService.findByName(countryName);
        return countries;
    }
}