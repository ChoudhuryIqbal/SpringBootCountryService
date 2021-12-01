package com.countryService.demo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.countryService.demo.beans.Country;
import com.countryService.demo.repositories.CountryRepository;
import com.countryService.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = { ServiceMackitoTests.class })
public class ServiceMackitoTests {

	@Mock
	CountryRepository countryRep;

	@InjectMocks
	CountryService countryService;

	public List<Country> myCountries;

	@Test
	@Order(1)
	public void test_getAllCountries() {
		myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "India", "Delhi"));
		myCountries.add(new Country(2, "Canada", "Torrento"));
		// when (countryRep.findAll().thenReturn(myCountries));
		when(countryRep.findAll()).thenReturn(myCountries);

		assertEquals(2, countryService.getAllCountries().size());

		/// countryService.getAllCountries();
	}

	@Test
	@Order(2)
	public void test_getCountryById() {
		myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "India", "Delhi"));
		myCountries.add(new Country(2, "Canada", "Torrento"));
		int countryID = 1;
		when(countryRep.findAll()).thenReturn(myCountries);
		Country p = countryService.getCountrbyId(countryID);
		int c = p.getId();

		assertEquals(countryID, countryService.getCountrbyId(countryID).getId());
	}

	@Test
	@Order(3)
	public void test_getCountryByName() {
		myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "India", "Delhi"));
		myCountries.add(new Country(2, "Canada", "Torrento"));
		String countryName = "India";
		when(countryRep.findAll()).thenReturn(myCountries);
		assertEquals(countryName, countryService.getCountrybyName(countryName).getCountryName());
	}

	@Test
	@Order(4)
	public void test_AddCountry() {
		myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "India", "Delhi"));
		myCountries.add(new Country(2, "Canada", "Torrento"));

		Country newCountry = new Country(3, "USA", "Washington DC");
		when(countryRep.save(newCountry)).thenReturn(newCountry);
		assertEquals(newCountry, countryService.addCountry(newCountry));
	}

	@Test
	@Order(5)
	public void test_UpdateCountry() {
		myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "India", "Delhi"));
		myCountries.add(new Country(2, "Canada", "Torrento"));

		Country newCountry = new Country(2, "USA", "Washing");
		when(countryRep.save(newCountry)).thenReturn(newCountry);
		assertEquals(newCountry, countryService.updateCountry(newCountry));
	}
	
	@Test
	@Order(6)
	public void test_DeleteCountry() {
		myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "India", "Delhi"));
		myCountries.add(new Country(2, "Canada", "Torrento"));

		Country newCountry = new Country(2, "USA", "Washing");
	    countryRep.deleteById(2);
		verify (countryRep,times(1)).deleteById(2);
		//assertEquals(newCountry, countryService.updateCountry(newCountry));
	}
	

}
