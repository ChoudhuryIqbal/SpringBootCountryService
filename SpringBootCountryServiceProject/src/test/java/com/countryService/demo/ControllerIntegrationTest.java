package com.countryService.demo;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.countryService.demo.beans.Country;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTest {
	
	@Test @Order (1)
	void getAllCountriesIntegrationTest() throws Exception {
		String expected="[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"countryName\": \"India\",\r\n"
				+ "        \"countryCapital\": \"Delhi\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 2,\r\n"
				+ "        \"countryName\": \"USA\",\r\n"
				+ "        \"countryCapital\": \"Washington\"\r\n"
				+ "    }\r\n"
				+ "]";
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getcountries", String.class);
		
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	
	@Test @Order (2)
	void getAllCountryByIdIntegrationTest() throws Exception {
		String expected="{\r\n"
				+ "    \"id\": 2,\r\n"
				+ "    \"countryName\": \"USA\",\r\n"
				+ "    \"countryCapital\": \"Washington\"\r\n"
				+ "}";
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getCountries/2", String.class);
		
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	@Test @Order (3)
	void getAllCountryByNameIntegrationTest() throws Exception {
		String expected="{\r\n"
				+ "    \"id\": 2,\r\n"
				+ "    \"countryName\": \"USA\",\r\n"
				+ "    \"countryCapital\": \"Washington\"\r\n"
				+ "}";
		TestRestTemplate restTemplate=new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getcountries/countryname?name=USA", String.class);
		
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	@Test @Order (4)
	void addNewCountryIntegrationTest() throws Exception {
		Country country=new Country(4,"Bangladesh","Berlin");
		String expected="{\r\n"
				+ "    \"id\": 4,\r\n"
				+ "    \"countryName\": \"Bangladesh\",\r\n"
				+ "    \"countryCapital\": \"Dhaka\"\r\n"
				+ "}";
		TestRestTemplate restTemplate=new TestRestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity <Country> request=new HttpEntity <Country> (country,headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/addCountry",request, String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		
	}
	@Test @Order (5)
	void updateCountryIntegrationTest() throws Exception {
		Country country=new Country(6,"Canada","Torento");
		String expected="{\r\n"
				+ "    \"id\": 6,\r\n"
				+ "    \"countryName\": \"Canada\",\r\n"
				+ "    \"countryCapital\": \"Torento\"\r\n"
				+ "}";
		TestRestTemplate restTemplate=new TestRestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity <Country> request=new HttpEntity <Country> (country,headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/updatecountry/4",HttpMethod.PUT, request,String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		assertEquals(HttpStatus.OK,response.getStatusCode());
		
	}
	@Test @Order (6)
	void deleteCountryIntegrationTest() throws Exception {
		Country country=new Country(6,"Canada","Torento");
		String expected="{\r\n"
				+ "    \"id\": 6,\r\n"
				+ "    \"countryName\": \"Canada\",\r\n"
				+ "    \"countryCapital\": \"Torento\"\r\n"
				+ "}";
		TestRestTemplate restTemplate=new TestRestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity <Country> request=new HttpEntity <Country> (country,headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/deletecountry/4",HttpMethod.DELETE, request,String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		assertEquals(HttpStatus.OK,response.getStatusCode());
		
	}
}
