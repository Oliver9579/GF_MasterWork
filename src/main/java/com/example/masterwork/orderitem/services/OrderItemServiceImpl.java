package com.example.masterwork.orderitem.services;

import com.example.masterwork.orderitem.DTOs.OrderItemDTO;
import com.example.masterwork.orderitem.models.OrderItem;
import com.example.masterwork.orderitem.repositories.OrderItemRepository;
import com.example.masterwork.restaurant.services.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

  private OrderItemRepository orderItemRepository;
  private RestaurantService restaurantService;


  @Override
  public OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
    return new OrderItemDTO(orderItem.getId(),
            restaurantService.getById(orderItem.getOrder().getRestaurant().getId()).getName(),
            orderItem.getMenu().getName(), orderItem.getQuantity(), orderItem.getPrice());
  }

  @Override
  public OrderItem save(OrderItem orderItem) {
    return orderItemRepository.save(orderItem);
  }

}
