package com.example.masterwork.menu.models;

import com.example.masterwork.orderitem.models.OrderItem;
import com.example.masterwork.restaurant.models.Restaurant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  private String name;

  @NotNull
  private String description;

  @NotNull


  // Many-to-many relationship with Restaurant
  @ManyToMany
  @JoinTable(name = "restaurant_menu",
          joinColumns = @JoinColumn(name = "menu_id"),
          inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
  private List<Restaurant> restaurants = new ArrayList<>();

  // Many-to-many relationship with OrderItem
  @ManyToMany(mappedBy = "menu")
  private List<OrderItem> orderItems = new ArrayList<>();

  // getters and setters
}

