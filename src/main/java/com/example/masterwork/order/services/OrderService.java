package com.example.masterwork.order.services;

import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.order.DTOs.OrderDTO;
import com.example.masterwork.order.DTOs.OrderDTOList;
import com.example.masterwork.order.DTOs.OrderNewStatusDTO;
import com.example.masterwork.order.DTOs.OrderRequestDTO;
import com.example.masterwork.order.models.Order;
import com.example.masterwork.orderitem.models.OrderItem;
import com.example.masterwork.restaurant.models.Restaurant;
import com.example.masterwork.user.models.User;

import java.util.HashMap;
import java.util.List;

public interface OrderService {

  OrderDTO getOrderById(Integer id, Integer userId);

  OrderDTOList getAllOrderById(Integer userId);

  OrderDTO convertToOrderDTO(Order order, Integer userId);

  OrderDTO createNewOrder(OrderRequestDTO newOrder, User user);

  List<Menu> validateAndGetMenus(OrderRequestDTO newOrder, Restaurant restaurant);

  HashMap<Integer, Integer> getQuantities(OrderRequestDTO newOrder);

  List<OrderItem> createOrderItems(List<Menu> menus, HashMap<Integer, Integer> quantities);

  Order createOrder(User user, Restaurant restaurant, List<Menu> menus, List<OrderItem> newOrderItems);

  OrderDTO setOrderNewStatus(User user, OrderNewStatusDTO newOrderStatus, Integer orderId);

  OrderDTOList getOrdersByStatus(String status, User user);

  OrderDTO deleteOrderById(User user, Integer orderId);


}
