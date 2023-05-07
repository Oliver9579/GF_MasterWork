package com.example.masterwork.order.utils;

public enum OrderStatus {

  ORDER_CONFIRMED,
  READY_FOR_DELIVERY,
  UNDER_SHIPMENT,
  DELETED,
  DELIVERED;


  @Override
  public String toString() {
    return this.name();
  }
}
