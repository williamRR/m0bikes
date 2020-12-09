package com.mobike.demo.serviceImpl;

import com.mobike.demo.dao.IUserDAO;
import com.mobike.demo.entity.Role;
import com.mobike.demo.entity.Usuario;
import com.mobike.demo.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

  @Autowired
  private IUserDAO iUserDao;

  @Override
  public List<Usuario> findAll() {
    return (List<Usuario>) iUserDao.findAll();
  }

  @Override
  public void save(Usuario usuario) {
    iUserDao.save(usuario);
  }

  @Override
  public Usuario findOne(Long id) {
    return iUserDao.findById(id).orElse(null);
  }

  @Override
  public void delete(Long id) {
    iUserDao.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = iUserDao.findByUsername(username);

    if (usuario == null) {
      throw new UsernameNotFoundException("Usuario no encontrado!");
    }
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role : usuario.getRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
    }
    return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
  }

  @Override
  public Usuario findByUsername(String username) {
    return iUserDao.findByUsername(username);
  }

  @Override
  public Usuario findByEmail(String email) {
    return iUserDao.findByEmail(email);
  }

  @Override
  public Boolean existData(String email, String username) {
    boolean test = iUserDao.existsByUsername(username) || iUserDao.existsByEmail(email);
    return test;
  }
}
