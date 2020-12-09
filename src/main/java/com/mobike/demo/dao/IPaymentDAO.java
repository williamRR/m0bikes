package com.mobike.demo.dao;

import com.mobike.demo.entity.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPaymentDAO extends CrudRepository<Payment, Long> {
  List<Payment> findByUserId(Long id);
}
