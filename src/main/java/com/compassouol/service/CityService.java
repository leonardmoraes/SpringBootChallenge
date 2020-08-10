package com.compassouol.service;

import com.compassouol.model.City;

import java.util.List;
import java.util.Map;

public interface CityService {

    List<City> search(Map filter);
    Long create(City data);
    Long update(City data) throws Exception;

}
