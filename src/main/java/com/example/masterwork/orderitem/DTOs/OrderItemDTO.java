package com.example.masterwork.orderitem.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

  @NotNull
  private Integer id;
  @NotBlank
  private String restaurant;
  @NotBlank
  private String menu;
  @NotNull
  private Integer quantity;
  @NotNull
  private Integer price;

}
