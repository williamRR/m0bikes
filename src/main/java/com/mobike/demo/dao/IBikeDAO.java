package com.mobike.demo.dao;

import com.mobike.demo.entity.Bike;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IBikeDAO extends CrudRepository<Bike, Long> {

  List<Bike> findByAvailableFlag(Boolean available);
}
