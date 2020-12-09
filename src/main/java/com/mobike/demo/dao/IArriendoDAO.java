package com.mobike.demo.dao;

import com.mobike.demo.entity.Arriendo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IArriendoDAO extends CrudRepository<Arriendo, Long> {
  List<Arriendo> findByUserIdOrderByCreatedAtDesc(Long id);

}
