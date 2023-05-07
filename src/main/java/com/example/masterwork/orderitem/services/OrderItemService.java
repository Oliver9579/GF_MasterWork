package com.example.masterwork.orderitem.services;

import com.example.masterwork.orderitem.DTOs.OrderItemDTO;
import com.example.masterwork.orderitem.models.OrderItem;

public interface OrderItemService {

  OrderItemDTO convertToOrderItemDTO(OrderItem orderItem);

  OrderItem save(OrderItem orderItem);

}
