package com.mobike.demo.serviceImpl;

import com.mobike.demo.dao.IAuthorityDAO;
import com.mobike.demo.entity.Role;
import com.mobike.demo.services.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements IAuthorityService {

  @Autowired
  IAuthorityDAO authorityDAO;

  @Override
  public void save(Role role) {
    authorityDAO.save(role);
  }

//  @Autowired
//  IAuthorityDAO authorityDAO;
//
//  @Override
//  public void save(Role authority) {
//    authorityDAO.save(authority);
//  }
}
