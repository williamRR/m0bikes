package com.mobike.demo.services;

import com.mobike.demo.entity.Payment;

import java.util.List;

public interface IPaymentService {

  public List<Payment> findAll();

  public void save(Payment payment);

  public Payment findOne(Long id);

  public void delete(Long id);
}
