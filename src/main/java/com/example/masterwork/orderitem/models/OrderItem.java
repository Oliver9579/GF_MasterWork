package com.example.masterwork.orderitem.models;

import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.order.models.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  private Integer quantity;

  @NotNull
  private Integer price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToMany
  @JoinTable(
          name = "order_items_menu",
          joinColumns = @JoinColumn(name = "order_items_id"),
          inverseJoinColumns = @JoinColumn(name = "menu_id"))
  private List<Menu> menu;

}

