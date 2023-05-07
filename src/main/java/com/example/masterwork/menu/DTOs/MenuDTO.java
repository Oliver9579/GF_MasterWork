package com.example.masterwork.menu.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuDTO {

  @NotNull
  private Integer id;
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NotNull
  private Integer price;

}
