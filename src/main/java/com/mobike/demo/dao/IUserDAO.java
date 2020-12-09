package com.mobike.demo.dao;

import com.mobike.demo.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUserDAO extends CrudRepository<Usuario, Long> {

  Usuario findByUsername(String username);

  Usuario findByEmail(String email);

  Boolean existsByEmail(String email);

  Boolean existsByUsername(String username);
}
