package com.example.masterwork.orderitem.models;

import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.order.models.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
@ToString
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  private Integer quantity;

  @NotNull
  private Integer price;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id", nullable = false)
  private Menu menu;

  public OrderItem(Integer quantity, Integer price, Menu menu) {
    this.quantity = quantity;
    this.price = price;
    this.menu = menu;
    this.order = new Order();
  }

}

