package com.countryService.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countryService.demo.beans.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {

	
}
