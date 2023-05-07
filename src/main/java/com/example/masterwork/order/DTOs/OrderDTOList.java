package com.example.masterwork.order.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTOList {

  @NotNull
  private List<OrderDTO> orders;

}
