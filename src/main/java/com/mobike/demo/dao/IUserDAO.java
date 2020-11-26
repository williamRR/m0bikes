package com.mobike.demo.dao;

import com.mobike.demo.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUserDAO extends CrudRepository<Usuario, Long> {

  public Usuario findByUsername(String username);
}
