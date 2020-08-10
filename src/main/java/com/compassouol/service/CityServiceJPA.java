package com.compassouol.service;

import com.compassouol.model.City;
import com.compassouol.repository.CityRepository;
import com.compassouol.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityServiceJPA implements CityService {

    @Autowired
    CityRepository repository;

    public List<City> search(Map filter) {
        Long id = ConvertUtil.getLongValue(filter.get("id"));
        if (id != null) {
            Optional<City> cityById = repository.findById(id);
            return cityById.isPresent() ? Arrays.asList(cityById.get()) : Collections.EMPTY_LIST;
        }

        String name = (String) filter.get("name");
        String state = (String) filter.get("state");

        if (name != null && state != null) {
            return repository.findByNameAndState(name, state);
        }
        else if (name != null) {
            return repository.findByName(name);
        }
        else if (state != null) {
            return repository.findByState(state);
        }

        return repository.findAll();
    }

    public Long create(City data) {
        City city = repository.save(data);
        return city.getId();
    }

    public Long update(City data) throws Exception {
        Optional<City> cityById = repository.findById(data.getId());
        if (cityById.isPresent()) {
            City city = repository.save(data);
            return city.getId();
        } else {
            throw new Exception(String.format("City %d not found.", data.getId()));
        }
    }
}
