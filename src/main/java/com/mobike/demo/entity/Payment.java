package com.mobike.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table
public class Payment {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Size(min = 16, max = 16)
  private String numero;
  @Size(min = 4, max = 4)
  private String expiracion;
  @Size(min = 3, max = 3)
  private String crc;
  @Column(name = "user_id")
  private Long userId;

  public Long getUser_id() {
    return userId;
  }

  public void setUser_id(Long user_id) {
    this.userId = user_id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getExpiracion() {
    return expiracion;
  }

  public void setExpiracion(String expiracion) {
    this.expiracion = expiracion;
  }

  public String getCrc() {
    return crc;
  }

  public void setCrc(String crc) {
    this.crc = crc;
  }
}
