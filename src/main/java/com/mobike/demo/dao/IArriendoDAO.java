package com.mobike.demo.dao;

import com.mobike.demo.entity.Arriendo;
import org.springframework.data.repository.CrudRepository;

public interface IArriendoDAO extends CrudRepository<Arriendo, Long> {

}
