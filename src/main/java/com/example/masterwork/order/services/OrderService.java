package com.example.masterwork.order.services;

import com.example.masterwork.order.DTOs.OrderDTO;
import com.example.masterwork.order.models.Order;

public interface OrderService {

  OrderDTO getOrderById(Integer id, Integer userId);

  OrderDTO convertToOrderDTO(Order order, Integer userId);
}
