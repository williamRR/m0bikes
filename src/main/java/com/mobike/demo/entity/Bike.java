package com.mobike.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "bikes")

public class Bike implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty
  private String size;
  @NotEmpty
  private String branch;
  @NotNull
  @Column(name = "available_flag")
  private boolean availableFlag;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public boolean isAvailableFlag() {
    return availableFlag;
  }

  public void setAvailableFlag(boolean availableFlag) {
    this.availableFlag = availableFlag;
  }


}
