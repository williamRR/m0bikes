package com.mobike.demo.dao;

import com.mobike.demo.entity.Payment;
import org.springframework.data.repository.CrudRepository;

public interface IPaymentDAO extends CrudRepository<Payment, Long> {
}
