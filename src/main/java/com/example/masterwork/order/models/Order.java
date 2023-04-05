package com.example.masterwork.order.models;

import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.order.utils.OrderStatus;
import com.example.masterwork.orderitem.models.OrderItem;
import com.example.masterwork.restaurant.models.Restaurant;
import com.example.masterwork.user.models.User;
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
@Table(name = "orders")
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "order_menu",
          joinColumns = @JoinColumn(name = "order_id"),
          inverseJoinColumns = @JoinColumn(name = "menu_id"))
  private List<Menu> menus = new ArrayList<>();

}

