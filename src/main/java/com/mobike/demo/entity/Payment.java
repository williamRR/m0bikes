package com.mobike.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
public class Payment {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty
  private String numero;
  @NotEmpty
  private String expiracion;
  @NotEmpty
  private String crc;
  private Long user_id;

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
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
