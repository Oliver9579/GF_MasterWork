package com.example.masterwork.order.controllers;

import com.example.masterwork.order.DTOs.OrderDTO;
import com.example.masterwork.order.DTOs.OrderDTOList;
import com.example.masterwork.order.DTOs.OrderNewStatusDTO;
import com.example.masterwork.order.DTOs.OrderRequestDTO;
import com.example.masterwork.order.services.OrderService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class OrderController {

  private OrderService orderService;
  private UserService userService;


  @GetMapping("/orders/{id}")
  public ResponseEntity<OrderDTO> getOrderById(UsernamePasswordAuthenticationToken auth,
                                               @PathVariable(name = "id") Integer orderId) {
    Integer userId = ((User) auth.getPrincipal()).getId();
    return ResponseEntity.ok().body(orderService.getOrderById(orderId, userId));
  }

  @GetMapping("/orders")
  public ResponseEntity<OrderDTOList> getAllOrdersByStatus(UsernamePasswordAuthenticationToken auth,
                                                           @RequestParam(name = "status", required = false) String status) {
    Integer userId = ((User) auth.getPrincipal()).getId();
    User user = userService.getById(userId);
    if (status == null) {
      return ResponseEntity.ok().body(orderService.getAllOrderById(userId));
    } else {
      return ResponseEntity.ok().body(orderService.getOrdersByStatus(status, user));
    }
  }

  @PostMapping("/orders")
  public ResponseEntity<OrderDTO> createNewOrder(UsernamePasswordAuthenticationToken auth,
                                                 @Valid @RequestBody OrderRequestDTO newOrder) {
    Integer userId = ((User) auth.getPrincipal()).getId();
    User user = userService.getById(userId);
    return ResponseEntity.ok().body(orderService.createNewOrder(newOrder, user));
  }

  @PutMapping("/orders/{id}/status")
  public ResponseEntity<OrderDTO> setNewOrderStatus(UsernamePasswordAuthenticationToken auth,
                                                    @Valid @RequestBody OrderNewStatusDTO newOrderStatus,
                                                    @PathVariable(name = "id") Integer orderId) {
    Integer userId = ((User) auth.getPrincipal()).getId();
    User user = userService.getById(userId);

    return ResponseEntity.ok().body(orderService.setOrderNewStatus(user, newOrderStatus, orderId));
  }

  @DeleteMapping("/orders/{id}")
  public ResponseEntity<OrderDTO> deleteOrderById(UsernamePasswordAuthenticationToken auth,
                                                  @PathVariable(name = "id") Integer orderId) {
    Integer userId = ((User) auth.getPrincipal()).getId();
    User user = userService.getById(userId);
    return ResponseEntity.ok().body(orderService.deleteOrderById(user, orderId));
  }

}
