package com.example.masterwork.menu.models;

import com.example.masterwork.orderitem.models.OrderItem;
import com.example.masterwork.restaurant.models.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
  private Integer price;

  @ManyToMany
  @JoinTable(name = "restaurant_menu",
          joinColumns = @JoinColumn(name = "menu_id"),
          inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
  private List<Restaurant> restaurants = new ArrayList<>();

  @ManyToMany(mappedBy = "menu")
  private List<OrderItem> orderItems = new ArrayList<>();

}

