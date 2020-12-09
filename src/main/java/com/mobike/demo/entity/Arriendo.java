package com.mobike.demo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "arriendos")
public class Arriendo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "user_id")
  private Long userId;
  @Column(name = "bike_id")
  private Long bikeId;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "ended_at")
  private LocalDateTime endedAt;
  private Boolean enabled;
  private Long valorTotal;

  public Arriendo() {
  }

  public Long getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(Long valorTotal) {
    this.valorTotal = valorTotal;
  }

  public LocalDateTime getEndedAt() {
    return endedAt;
  }

  public void setEndedAt(LocalDateTime endedAt) {
    this.endedAt = endedAt;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getBikeId() {
    return bikeId;
  }

  public void setBikeId(Long bikeId) {
    this.bikeId = bikeId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
