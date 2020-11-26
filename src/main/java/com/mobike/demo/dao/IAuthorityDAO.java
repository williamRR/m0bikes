package com.mobike.demo.dao;

import com.mobike.demo.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface IAuthorityDAO extends CrudRepository<Role, Long> {
}
