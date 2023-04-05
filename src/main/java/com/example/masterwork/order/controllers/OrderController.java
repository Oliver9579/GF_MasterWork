package com.example.masterwork.order.controllers;

import com.example.masterwork.order.DTOs.OrderDTO;
import com.example.masterwork.order.services.OrderService;
import com.example.masterwork.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderController {

  private OrderService orderService;

  @GetMapping("/orders/{id}")
  public ResponseEntity<OrderDTO> getOrderById(UsernamePasswordAuthenticationToken auth,
                                               @PathVariable(name = "id") Integer orderId) {
    Integer userId = ((User)auth.getPrincipal()).getId();
    return ResponseEntity.ok().body(orderService.getOrderById(orderId, userId));
  }

}
