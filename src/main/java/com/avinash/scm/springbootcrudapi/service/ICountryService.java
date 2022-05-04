package com.avinash.scm.springbootcrudapi.service;


import com.avinash.scm.springbootcrudapi.model.Country;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICountryService {
    Page<Country> findAll(int pageNo, int pageSize);
    public List<Country> findAll();
    List<Country> findByName(String countryName);
    public Country getCountryByName(String countryName);

}
