package com.mobike.demo.services;

import com.mobike.demo.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

  List<Usuario> findAll();

  void save(Usuario usuario);

  Usuario findOne(Long id);

  void delete(Long id);

  Usuario findByUsername(String username);

  Usuario findByEmail(String email);

  Boolean existData(String email, String username);
}

