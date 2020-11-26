package com.mobike.demo.serviceImpl;

import com.mobike.demo.dao.IPaymentDAO;
import com.mobike.demo.entity.Payment;
import com.mobike.demo.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {

  @Autowired
  private IPaymentDAO medioPagoDAO;

  @Override
  @Transactional(readOnly = true)
  public List<Payment> findAll() {
    return (List<Payment>) medioPagoDAO.findAll();
  }

  @Override
  @Transactional
  public void save(Payment payment) {
    medioPagoDAO.save(payment);
  }

  @Override
  @Transactional(readOnly = true)
  public Payment findOne(Long id) {
    return medioPagoDAO.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    medioPagoDAO.deleteById(id);
  }
}
