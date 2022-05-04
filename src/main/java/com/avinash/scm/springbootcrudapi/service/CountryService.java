package com.avinash.scm.springbootcrudapi.service;

import com.avinash.scm.springbootcrudapi.dao.ICountryDao;
import com.avinash.scm.springbootcrudapi.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService implements ICountryService {

    @Autowired
    private ICountryDao iCountryDao;

    @Override
    public Page<Country> findAll(int pageNo, int pageSize) {
        return iCountryDao.findAll(pageNo, pageSize);
    }

    @Override
    public List<Country> findAll() {
        return iCountryDao.findAll();
    }

    @Override
    public List<Country> findByName(String countryName) {

        return (List<Country>) iCountryDao.findByName(countryName);
    }

    @Override
    public Country getCountryByName(String countryName) {

        return iCountryDao.getCountryByName(countryName);
    }

}
