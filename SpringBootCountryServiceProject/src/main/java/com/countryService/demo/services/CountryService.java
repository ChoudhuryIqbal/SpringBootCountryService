package com.countryService.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.countryService.demo.beans.Country;
import com.countryService.demo.controllers.AddResponse;
import com.countryService.demo.repositories.CountryRepository;

@Component
@Service
public class CountryService {

	@Autowired
	CountryRepository countryrepo;

	public List getAllCountries() {
		return countryrepo.findAll();

	}

	public Country getCountrbyId(int id) {
		List<Country> countries = countryrepo.findAll();
		Country country=null;
		for ( Country con: countries) {
			if(con.getId()==id) {
				country=con;
				
			}
		}
		
		return country;
	}

	public Country getCountrybyName(String countryName) {
		List<Country> countries = countryrepo.findAll();
		Country country=null;
		for ( Country con: countries) {
			if(con.getCountryName().equalsIgnoreCase(countryName)) {
				country=con;
				
			}
		}
		
		return country;
	}

	public Country addCountry(Country country) {
		//countryrepo.
		
		country.setId(getMaxId());
		countryrepo.save(country);
		return country;
		

	}

	public  int getMaxId() {
		
		return countryrepo.findAll().size()+1;
	}

	public Country updateCountry(Country country) {
		countryrepo.save(country);
		return country;
	}

	public AddResponse deleteCountry(int id) {
		countryrepo.deleteById(id);
		AddResponse res=new AddResponse();
		res.setMsg("Country Deleted ! ");
		res.setId(id);
		return res;
	}

	
	  static HashMap<Integer, Country> countryIdMap;
	  
	  public CountryService() { countryIdMap = new HashMap<Integer, Country>();
	  
	  Country indiaCountry = new Country(1, "India", "Delhi"); Country usaCountry =
	  new Country(2, "USA", "Washington"); Country ukCountry = new Country(3, "UK",
	  "London");
	  
	  countryIdMap.put(1, indiaCountry); countryIdMap.put(2, usaCountry);
	  countryIdMap.put(3, ukCountry); }
	  
	/**  @SuppressWarnings({ "rawtypes", "unchecked" }) public List getAllCountries()
	  { List countries = new ArrayList(countryIdMap.values()); return countries; }
	  
	  public Country getCountryById(int id) { Country country =
	  countryIdMap.get(id); // countryIdMap.get(id); return country; }
	  
	  @SuppressWarnings("unlikely-arg-type") public Country getCountryByName(String
	  countryName) {
	  
	  Country country = null; for (int i : countryIdMap.keySet()) { if
	  (countryIdMap.get(i).getCountryName().equals(countryName)) country =
	  countryIdMap.get(i); } country = countryIdMap.get(countryName); return
	  country; }
	  
	  public Country addCountry(Country country) { country.setId(getMaxId());
	  countryIdMap.put(country.getId(), country); return country; }
	  
	  public static int getMaxId() { int max = 0; for (int id :
	  countryIdMap.keySet()) { if (max <= id) { max = id; } } return max + 1; }
	  
	  public AddResponse deleteCountry(int id) { countryIdMap.remove(id);
	  AddResponse res = new AddResponse(); res.setMsg("Country deleted ... ");
	  res.setId(id); return res; }
	  
	  public Country updateCountry(Country country) {
	  
	  if(country.getId()>0) countryIdMap.put(country.getId(), country); return
	  country;
	  
	  }**/
	 

}
