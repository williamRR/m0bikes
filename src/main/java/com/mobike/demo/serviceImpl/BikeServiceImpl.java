package com.mobike.demo.serviceImpl;

import com.mobike.demo.dao.IBikeDAO;
import com.mobike.demo.entity.Bike;
import com.mobike.demo.services.IBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BikeServiceImpl implements IBikeService {

  @Autowired
  private IBikeDAO iBikeDao;

  @Override
  @Transactional(readOnly = true)
  public List<Bike> findAll() {
    return (List<Bike>) iBikeDao.findAll();
  }

  @Override
  public List<Bike> findAvailables() {
    return iBikeDao.findByAvailableFlag(true);
  }

  @Override
  @Transactional
  public void save(Bike bike) {
    iBikeDao.save(bike);
  }

  @Override
  @Transactional(readOnly = true)
  public Bike findOne(Long id) {
    return iBikeDao.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    iBikeDao.deleteById(id);
  }
}
