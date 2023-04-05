package com.example.masterwork.orderitem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {

  private Integer id;
  private String restaurant;
  private String menu;
  private Integer quantity;
  private Integer price;
}
