package com.example.masterwork.order.services;

import com.example.masterwork.exceptions.IdNotFoundException;
import com.example.masterwork.order.DTOs.OrderDTO;
import com.example.masterwork.order.models.Order;
import com.example.masterwork.order.repositories.OrderRepository;
import com.example.masterwork.orderitem.services.OrderItemService;
import com.example.masterwork.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;
  private OrderItemService orderItemService;
  private UserService userService;

  @Override
  public OrderDTO getOrderById(Integer orderId, Integer userId) {
    return convertToOrderDTO(orderRepository.findById(orderId).orElseThrow(IdNotFoundException::new), userId);
  }

  @Override
  public OrderDTO convertToOrderDTO(Order order, Integer userId) {
    userService.isUserIdMatching(userId, order);
    return new OrderDTO(order.getId(),
            order.getOrderItems().stream().mapToInt(value -> value.getPrice()).sum(),
            order.getDeliveryAddress(), order.getStatus(), order.getCreated(),
            order.getOrderItems().stream()
                    .map(orderItem -> orderItemService.convertToOrderItemDTO(orderItem))
                    .collect(Collectors.toList()));
  }
}
