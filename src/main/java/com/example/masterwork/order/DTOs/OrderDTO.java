package com.example.masterwork.order.DTOs;

import com.example.masterwork.order.utils.OrderStatus;
import com.example.masterwork.orderitem.DTOs.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {

  private Integer id;
  private Integer totalAmount;
  private String deliveryAddress;
  private OrderStatus status;
  private Long created;
  private List<OrderItemDTO> orders;

}
