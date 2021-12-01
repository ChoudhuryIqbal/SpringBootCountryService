package com.countryService.demo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.countryService.demo.beans.Country;
import com.countryService.demo.services.CountryService;

@RestController
public class CountryController {
	@Autowired
	CountryService countryService;



	@SuppressWarnings("rawtypes")
	@GetMapping("/getcountries")
	public ResponseEntity<List<Country>> getCountries() {
		
		try {
		List <Country> countries =countryService.getAllCountries();
			return new ResponseEntity <List<Country>> (countries,HttpStatus.FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity <> (HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getCountries/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable (value="id") int id) {
		
		try {
			Country country =countryService.getCountrbyId(id);
			return new ResponseEntity <Country> (country,HttpStatus.FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity <> (HttpStatus.NOT_FOUND);
		}
		
		
	}

	@GetMapping("/getcountries/countryname")
	public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String countryName) {
		
		try 
		{
			Country country=countryService.getCountrybyName(countryName);
			return new ResponseEntity <Country> (country,HttpStatus.FOUND);
		}
		catch(Exception ex) {
			return new ResponseEntity <> (HttpStatus.NOT_FOUND);
		}
	
	}

	@PostMapping("/addCountry")
	public ResponseEntity<Country> addCountry(@RequestBody Country country) {
		

		try {
			country = countryService.addCountry(country);
			
			return new ResponseEntity <Country> (country,HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return new ResponseEntity <>(HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/updatecountry/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable (value="id") int id,  @RequestBody Country country) {
		
		try {
			Country existCountry = countryService.getCountrbyId(id);
			existCountry.setCountryName(country.getCountryName());
			existCountry.setCountryCapital(country.getCountryCapital());
			
			return new ResponseEntity <Country>(countryService.updateCountry(existCountry),HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return new ResponseEntity <>(HttpStatus.CONFLICT);
		}
		
	
	}

	@DeleteMapping("/deletecountry/{id}")
	public ResponseEntity<Country> deleteCountry(@PathVariable (value="id") int id) {
		Country country=null;
		try {
			country = countryService.getCountrbyId(id);
			
			countryService.deleteCountry(id);
			
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return new ResponseEntity <>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity <Country>(country,HttpStatus.OK);
		
	}

}
