package com.mobike.demo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "arriendos")
public class Arriendo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "user_id")
  private Long userId;
  @Column(name = "bike_id")
  private String bikeId;
  @Column(name = "created_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;
}
