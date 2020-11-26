package com.mobike.demo.services;

import com.mobike.demo.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

  public List<Usuario> findAll();

  public void save(Usuario usuario);

  public Usuario findOne(Long id);

  public void delete(Long id);

  public Usuario findByUsername(String username);
}

