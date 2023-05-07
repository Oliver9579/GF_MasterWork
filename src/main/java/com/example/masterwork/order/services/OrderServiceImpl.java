package com.example.masterwork.order.services;

import com.example.masterwork.exceptions.*;
import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.order.DTOs.OrderDTO;
import com.example.masterwork.order.DTOs.OrderDTOList;
import com.example.masterwork.order.DTOs.OrderNewStatusDTO;
import com.example.masterwork.order.DTOs.OrderRequestDTO;
import com.example.masterwork.order.models.Order;
import com.example.masterwork.order.repositories.OrderRepository;
import com.example.masterwork.order.utils.OrderStatus;
import com.example.masterwork.orderitem.models.OrderItem;
import com.example.masterwork.orderitem.services.OrderItemService;
import com.example.masterwork.restaurant.models.Restaurant;
import com.example.masterwork.restaurant.services.RestaurantService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;
  private OrderItemService orderItemService;
  private UserService userService;
  private RestaurantService restaurantService;

  @Override
  public OrderDTO getOrderById(Integer orderId, Integer userId) {
    Order order = orderRepository.findById(orderId).orElseThrow(IdNotFoundException::new);
    userService.isUserIdMatching(userId, order);
    return convertToOrderDTO(order, userId);
  }

  @Override
  public OrderDTOList getAllOrderById(Integer userId) {
    return new OrderDTOList(orderRepository.findAllByUserId(userId).stream()
            .map(order -> convertToOrderDTO(order, userId)).collect(Collectors.toList()));
  }

  @Override
  public OrderDTO convertToOrderDTO(Order order, Integer userId) {
    return new OrderDTO(order.getId(),
            order.getOrderItems().stream().mapToInt(OrderItem::getPrice).sum(),
            order.getDeliveryAddress(), order.getStatus(), order.getCreated(),
            order.getOrderItems().stream()
                    .map(orderItem -> orderItemService.convertToOrderItemDTO(orderItem))
                    .collect(Collectors.toList()));
  }

  @Override
  public OrderDTO createNewOrder(OrderRequestDTO newOrder, User user) {
    Restaurant restaurant = restaurantService.getById(newOrder.getRestaurantId());
    List<Menu> menus = validateAndGetMenus(newOrder, restaurant);

    HashMap<Integer, Integer> quantities = getQuantities(newOrder);
    List<OrderItem> newOrderItems = createOrderItems(menus, quantities);

    Order newUserOrder = createOrder(user, restaurant, menus, newOrderItems);

    Order saveOrder = orderRepository.save(newUserOrder);

    saveOrderItems(newOrderItems, saveOrder);

    return convertToOrderDTO(orderRepository.findById(saveOrder.getId()).get(), user.getId());
  }


  @Override
  public List<Menu> validateAndGetMenus(OrderRequestDTO newOrder, Restaurant restaurant) {
    if (newOrder.getItems() == null || newOrder.getItems().isEmpty()) throw new MissingOrderItemsException();

    List<Integer> newOrderItemsId = newOrder.getItems().stream()
            .map(orderItemRequestDTO -> orderItemRequestDTO.getMenuItemId())
            .collect(Collectors.toList());

    for (int i = 0; i < newOrderItemsId.size(); i++) {
      if (!restaurant.getMenus().stream()
              .map(Menu::getId)
              .collect(Collectors.toList()).contains(newOrderItemsId.get(i))) {
        throw new IdNotFoundException();
      }
    }

    return restaurant.getMenus().stream()
            .filter(menu -> newOrderItemsId.contains(menu.getId()))
            .collect(Collectors.toList());
  }

  @Override
  public HashMap<Integer, Integer> getQuantities(OrderRequestDTO newOrder) {
    HashMap<Integer, Integer> quantities = new HashMap<>();

    for (int i = 0; i < newOrder.getItems().size(); i++) {
      quantities.put(newOrder.getItems().get(i).getMenuItemId(), newOrder.getItems().get(i).getQuantity());
    }

    return quantities;
  }

  @Override
  public List<OrderItem> createOrderItems(List<Menu> menus, HashMap<Integer, Integer> quantities) {
    List<OrderItem> newOrderItems = new ArrayList<>();

    for (Map.Entry<Integer, Integer> entry : quantities.entrySet()) {
      OrderItem newOrderItem = new OrderItem(entry.getValue(),
              menus.stream().filter(menu -> menu.getId() == entry.getKey()).findFirst().get().getPrice() * entry.getValue(),
              menus.stream().filter(menu -> menu.getId() == entry.getKey()).findFirst().get());
      newOrderItems.add(newOrderItem);
    }
    return newOrderItems;
  }

  @Override
  public Order createOrder(User user, Restaurant restaurant, List<Menu> menus, List<OrderItem> newOrderItems) {
    return new Order(newOrderItems.stream().mapToInt(value -> value.getPrice()).sum(),
            user.getAddress(), OrderStatus.ORDER_CONFIRMED, System.currentTimeMillis() / 1000,
            user, restaurant);
  }

  @Override
  public OrderDTO setOrderNewStatus(User user, OrderNewStatusDTO newOrderStatus, Integer orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(IdNotFoundException::new);
    userService.isUserIdMatching(user.getId(), order);
    OrderStatus orderStatus = validateAndGetOrderStatus(newOrderStatus.getStatus());
    if (order.getStatus().equals(orderStatus)) throw new StatusDoesNotBeTheSameException();

    order.setStatus(orderStatus);

    orderRepository.save(order);

    return convertToOrderDTO(order, user.getId());

  }

  @Override
  public OrderDTOList getOrdersByStatus(String status, User user) {
    OrderStatus orderStatus = validateAndGetOrderStatus(status);

    return new OrderDTOList(user.getOrders().stream()
            .filter(order -> order.getStatus().equals(orderStatus))
            .map(order -> convertToOrderDTO(order, user.getId()))
            .collect(Collectors.toList()));

  }

  @Override
  public OrderDTO deleteOrderById(User user, Integer orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(IdNotFoundException::new);
    userService.isUserIdMatching(user.getId(), order);

    order.setStatus(OrderStatus.DELETED);
    orderRepository.delete(order);
    return convertToOrderDTO(order, user.getId());
  }

  private void saveOrderItems(List<OrderItem> newOrderItems, Order saveOrder) {
    newOrderItems.forEach(orderItem -> {
      orderItem.setOrder(saveOrder);
      orderItemService.save(orderItem);
    });

    saveOrder.setOrderItems(newOrderItems);
  }

  private OrderStatus validateAndGetOrderStatus(String status) {
    try {
      if (OrderStatus.valueOf(status).equals(OrderStatus.DELETED)) throw new IllegalArgumentException();
      return OrderStatus.valueOf(status);
    } catch (IllegalArgumentException e) {
      throw new StatusDoesNotExist();
    }
  }

}
