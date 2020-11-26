package com.mobike.demo.services;

import com.mobike.demo.entity.Bike;

import java.util.List;

public interface IBikeService {

  List<Bike> findAll();

  List<Bike> findAvailables();

  void save(Bike bike);

  Bike findOne(Long id);

  void delete(Long id);


}
