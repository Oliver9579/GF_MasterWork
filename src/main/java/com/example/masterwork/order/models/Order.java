package com.example.masterwork.order.models;

import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.order.utils.OrderStatus;
import com.example.masterwork.orderitem.models.OrderItem;
import com.example.masterwork.restaurant.models.Restaurant;
import com.example.masterwork.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@ToString
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Column(name = "total_amount")
  private Integer totalAmount;

  @NotNull
  @Column(name = "delivery_address")
  private String deliveryAddress;

  @NotNull
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @NotNull
  private Long created;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;

  @JsonIgnore
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<OrderItem> orderItems = new ArrayList<>();

  public Order(Integer totalAmount, String deliveryAddress, OrderStatus status, Long created, User user,
               Restaurant restaurant) {
    this.totalAmount = totalAmount;
    this.deliveryAddress = deliveryAddress;
    this.status = status;
    this.created = created;
    this.user = user;
    this.restaurant = restaurant;
    this.orderItems = new ArrayList<>();
  }

  public Order(Integer id, Integer totalAmount, String deliveryAddress,
               OrderStatus status, Long created, User user, Restaurant restaurant) {
    this.id = id;
    this.totalAmount = totalAmount;
    this.deliveryAddress = deliveryAddress;
    this.status = status;
    this.created = created;
    this.user = user;
    this.restaurant = restaurant;
  }
}

