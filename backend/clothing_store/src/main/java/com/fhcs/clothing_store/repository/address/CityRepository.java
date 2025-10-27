package com.fhcs.clothing_store.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.address.City;

public interface CityRepository extends JpaRepository<City, Integer> {
    
    public City findByCityName(String cityName);

}
