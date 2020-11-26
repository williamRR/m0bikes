package com.mobike.demo.serviceImpl;

import com.mobike.demo.dao.IArriendoDAO;
import com.mobike.demo.entity.Arriendo;
import com.mobike.demo.services.IArriendoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArriendoServiceImpl implements IArriendoService {

  @Autowired
  IArriendoDAO arriendoDAO;

  @Override
  public List<Arriendo> findAll() {
    return (List<Arriendo>) arriendoDAO.findAll();
  }

  @Override
  public void save(Arriendo arriendo) {
    arriendoDAO.save(arriendo);
  }

  @Override
  public Optional<Arriendo> findOne(Long id) {
    return arriendoDAO.findById(id);
  }

  @Override
  public void delete(Long id) {
    arriendoDAO.deleteById(id);
  }
}
