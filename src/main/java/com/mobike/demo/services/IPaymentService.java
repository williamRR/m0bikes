package com.mobike.demo.services;

import com.mobike.demo.entity.Payment;

import java.util.List;

public interface IPaymentService {

  List<Payment> findAll();

  void save(Payment payment);

  Payment findOne(Long id);

  void delete(Long id);

  List<Payment> findByUser(Long id);
}
