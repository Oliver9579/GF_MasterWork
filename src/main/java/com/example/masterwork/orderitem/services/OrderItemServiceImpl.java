package com.example.masterwork.orderitem.services;

import com.example.masterwork.orderitem.DTOs.OrderItemDTO;
import com.example.masterwork.orderitem.models.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

  @Override
  public OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
    return new OrderItemDTO(orderItem.getId(),
            orderItem.getMenu().getRestaurants().get(orderItem.getOrder().getRestaurant().getId() - 1).getName(),
            orderItem.getMenu().getName(), orderItem.getQuantity(), orderItem.getPrice());
  }
}
