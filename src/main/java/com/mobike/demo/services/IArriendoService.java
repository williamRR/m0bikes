package com.mobike.demo.services;

import com.mobike.demo.entity.Arriendo;

import java.util.List;
import java.util.Optional;

public interface IArriendoService {

  public List<Arriendo> findAll();

  public void save(Arriendo arriendo);

  public Optional<Arriendo> findOne(Long id);

  public void delete(Long id);
}
