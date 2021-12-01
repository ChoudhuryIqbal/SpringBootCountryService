package com.countryService.demo;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryService.demo.beans.Country;
import com.countryService.demo.controllers.CountryController;
import com.countryService.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {ControllerMockitoTest.class})
public class ControllerMockitoTest {
	
	@Mock
	CountryService countryService;
	
	@InjectMocks
	CountryController countryController;
	List <Country> myCountries;
	Country country;
	
	
	@Test
	@Order(1)
	public void test_getAllCountries() 
	{
		myCountries=new ArrayList <Country> ();
		myCountries.add(new Country(1,"India","Delhi"));
		myCountries.add(new Country(2,"USA","Washington"));
		when (countryService.getAllCountries()).thenReturn(myCountries);
		ResponseEntity<List<Country>> res = countryController.getCountries();
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
		assertEquals(2,res.getBody().size());
		
	}
	
	@Test
	@Order(2)
	public void test_getCountrybyID() {
		country=new Country(2,"Canada","Torento");
		int cuountryID=2;
		when (countryService.getCountrbyId(cuountryID)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryById(cuountryID);
		
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
		assertEquals(cuountryID,res.getBody().getId());
	
	
	}
	
	@Test
	@Order(3)
	public void test_getCountrybyName() {
		country=new Country(2,"Canada","Torento");
		String countryName="Canada";
	when(countryService.getCountrybyName(countryName)).thenReturn(country);
	ResponseEntity<Country> res = countryController.getCountryByName(countryName);
	assertEquals(HttpStatus.FOUND,res.getStatusCode());
	assertEquals(countryName, res.getBody().getCountryName());
		
		
	}
	
	
	@Test
	@Order(4)
	public void test_addCountry() {
		country=new Country(2,"Canada","Torento");
		when (countryService.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> res = countryController.addCountry(country);
	    assertEquals(HttpStatus.CREATED,res.getStatusCode());
	    assertEquals(country,res.getBody());
		
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		country=new Country(3,"Japan","Tokyo");
		
		int countryID=3;
		when(countryService.getCountrbyId(countryID)).thenReturn(country);
		
		when(countryService.updateCountry(country)).thenReturn(country);
		
		ResponseEntity<Country> res = countryController.updateCountry(3, country);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(country,res.getBody());
		assertEquals("Japan",res.getBody().getCountryName());
		assertEquals("Tokyo",res.getBody().getCountryCapital());
		
		
	}
	
	@Test
	@Order(6)
	public void test_deleteCountrry() {
		country=new Country(2,"Myanmar","Rangoon");
		
		int countryID=2;
		
		
		when(countryService.getCountrbyId(countryID)).thenReturn(country);
		ResponseEntity<Country> res = countryController.deleteCountry(countryID);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		
	}
	
	
	
	
	
	
	
	
	
	
	
		
	
}
