package com.compassouol.repository;

import com.compassouol.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.name like %?1%")
    List<City> findByName(String name);

    @Query("SELECT c FROM City c WHERE c.state = ?1")
    List<City> findByState(String state);

    @Query("SELECT c FROM City c WHERE c.name like %?1% and c.state = ?2")
    List<City> findByNameAndState(String name, String state);

}
