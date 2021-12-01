package com.countryService.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.platform.engine.TestExecutionResult.Status;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countryService.demo.beans.Country;
import com.countryService.demo.controllers.CountryController;
import com.countryService.demo.services.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { ControllerMockMvcTest.class })
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.countryService.demo")
@TestMethodOrder(OrderAnnotation.class)
public class ControllerMockMvcTest {
	@Autowired
	MockMvc mockMvc;

	@Mock
	CountryService countryService;

	@InjectMocks
	CountryController countryController;

	List<Country> myCountries;
	Country country;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();

	}

	@Test
	@Order(1)
	public void test_getAllCountryies() throws Exception {
		myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "India", "Delthi"));
		myCountries.add(new Country(2, "USA", "Washington"));
		when(countryService.getAllCountries()).thenReturn(myCountries);

		this.mockMvc.perform(get("/getcountries")).andExpect(status().isFound()).andDo(print());
	}

	@Test
	@Order(2)
	public void test_getCountryById() throws Exception {
		// myCountries = new ArrayList<Country>();
		int coutnryId = 2;
		country = new Country(2, "USA", "Washington");
		when(countryService.getCountrbyId(coutnryId)).thenReturn(country);
		this.mockMvc.perform(get("/getCountries/{id}", coutnryId)).andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington")).andDo(print());
	}

	@Test
	@Order(3)
	public void test_getCountryBName() throws Exception {
		// myCountries = new ArrayList<Country>();
		String coutnryName = "USA";
		country = new Country(2, "USA", "Washington");
		when(countryService.getCountrybyName(coutnryName)).thenReturn(country);
		this.mockMvc.perform(get("/getcountries/countryname").param("name", "USA")).andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
				.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington")).andDo(print());
	}

	@Test
	@Order(4)
	public void test_addCountry() throws Exception {
		country = new Country(3, "Germany", "Berlin");
		when(countryService.addCountry(country)).thenReturn(country);

		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(country);
		this.mockMvc.perform(post("/addCountry")

				.content(jsonBody).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andDo(print());

	}

	@Test
	@Order(5)
	public void test_updateCountry() throws Exception {
		country=new Country(4,"Japan","Tokyo");
		int countryId=3;
		when (countryService.getCountrbyId(3)).thenReturn(country);
		when(countryService.updateCountry(country)).thenReturn(country);
		
		ObjectMapper mapper=new ObjectMapper();
		String jsonBody=mapper.writeValueAsString(country);
		this.mockMvc.perform(put("/updatecountry/{id}",countryId)
			.content(jsonBody).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Japan"))
		.andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Tokyo"))
		.andDo(print());
		
	}
	
	
	@Test
	@Order(6)
	public void test_deleteCountry() throws Exception{
		country =new Country(3,"Japan","Tokyo");
		int countryId=3;
		when (countryService.getCountrbyId(countryId)).thenReturn(country);
		this.mockMvc.perform(delete("/deletecountry/{id}",countryId))
		.andExpect(status().isOk());
	}
}
