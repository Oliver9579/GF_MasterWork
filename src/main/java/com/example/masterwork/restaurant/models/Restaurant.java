package com.example.masterwork.restaurant.models;

import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.order.models.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  private String name;

  @NotNull
  @Column(name = "phone_number")
  private String phoneNumber;

  @NotNull
  private String address;

  // One-to-many relationship with Order
  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
  private List<Order> orders = new ArrayList<>();

  // Many-to-many relationship with Menu
  @ManyToMany(mappedBy = "restaurants")
  private List<Menu> menus = new ArrayList<>();

  // getters and setters
}
