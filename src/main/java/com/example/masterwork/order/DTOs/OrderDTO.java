package com.example.masterwork.order.DTOs;

import com.example.masterwork.order.utils.OrderStatus;
import com.example.masterwork.orderitem.DTOs.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

  @NotNull
  private Integer id;
  @NotNull
  private Integer totalAmount;
  @NotBlank
  private String deliveryAddress;
  @NotNull
  private OrderStatus status;
  @NotNull
  private Long created;
  @NotNull
  private List<OrderItemDTO> items;

  public OrderDTO(Integer totalAmount, OrderStatus status, List<OrderItemDTO> items) {
    this.totalAmount = totalAmount;
    this.status = status;
    this.items = items;
  }

}
