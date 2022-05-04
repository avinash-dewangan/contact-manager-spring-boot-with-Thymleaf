package com.avinash.scm.springbootcrudapi.dao;

import com.avinash.scm.springbootcrudapi.model.Country;
import com.avinash.scm.springbootcrudapi.repository.CountryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDao implements ICountryDao{
    private static final Logger LOGGER = LogManager.getLogger(CountryDao.class);

    @Autowired
    private CountryRepository repository;

    @Override
    public Page<Country> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Country> pagedResult = repository.findAll(paging);
        LOGGER.info("Successfully read by pagedResult: {}", pagedResult);
        return pagedResult;
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = (List<Country>) repository.findAll();
        LOGGER.info("List of all Country fetched: {}", countries);
        return countries;
    }

    @Override
    public List<Country> findByName(String countryName) {
        Country country1 = repository.countryListByName(countryName);

        Country country = repository.getCountryName(countryName);

        List<Country> countries = repository.findByName(countryName);
        return countries;
    }

    @Override
    public Country getCountryByName(String countryName) {

        Country country1 = repository.countryListByName(countryName);

        Country country = repository.getCountryName(countryName);

        return country;
    }

}



