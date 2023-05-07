package com.example.masterwork.order.DTOs;

import com.example.masterwork.orderitem.DTOs.OrderItemRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

  @NotNull
  private Integer restaurantId;
  @NotNull
  private List<OrderItemRequestDTO> items;

}
