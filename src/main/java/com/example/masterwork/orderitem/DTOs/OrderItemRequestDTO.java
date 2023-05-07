package com.example.masterwork.orderitem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class OrderItemRequestDTO {

  @NotNull
  private Integer menuItemId;
  @NotNull
  private Integer quantity;

}
