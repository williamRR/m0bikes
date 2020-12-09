package com.mobike.demo.serviceImpl;

import com.mobike.demo.dao.IArriendoDAO;
import com.mobike.demo.entity.Arriendo;
import com.mobike.demo.entity.Bike;
import com.mobike.demo.services.IArriendoService;
import com.mobike.demo.services.IBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArriendoServiceImpl implements IArriendoService {

  @Autowired
  IArriendoDAO arriendoDAO;
  @Autowired
  IBikeService bikeService;

  @Override
  public List<Arriendo> findAll() {
    return (List<Arriendo>) arriendoDAO.findAll();
  }

  @Override
  public void save(Arriendo arriendo) {
    Bike b = bikeService.findOne(arriendo.getBikeId());
    b.setAvailableFlag(false);
    bikeService.save(b);
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

  @Override
  public List<Arriendo> findByUserId(Long id) {
    return arriendoDAO.findByUserIdOrderByCreatedAtDesc(id);
  }

  @Override
  public Arriendo findCurrentRent(Long id) {
    List<Arriendo> arriendos = new ArrayList<>();
    arriendos = arriendoDAO.findByUserIdOrderByCreatedAtDesc(id);
    List<Arriendo> current = new ArrayList<>();
    if (arriendos.size() > 0) {
      current = arriendos.stream().filter(arriendo -> arriendo.getEnabled() == true).collect(Collectors.toList());
      if (current.size() > 0) {
        return current.get(0);
      } else return null;
    } else return null;
  }

  @Override
  public void endRent(Arriendo currentRent) {
    currentRent.setEnabled(false);
    Bike b = bikeService.findOne(currentRent.getBikeId());
    b.setAvailableFlag(true);
    bikeService.save(b);
    LocalDateTime endedAt = LocalDateTime.now();
    currentRent.setEndedAt(endedAt);
    Long valorTotal = Duration.between(
            currentRent.getCreatedAt(),
            currentRent.getEndedAt()).toMinutes() * 900 + 900;
    currentRent.setValorTotal(valorTotal);
    arriendoDAO.save(currentRent);
  }
}
