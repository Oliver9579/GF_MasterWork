package com.example.masterwork.order.controllers;

import com.example.masterwork.order.DTOs.OrderDTO;
import com.example.masterwork.order.DTOs.OrderNewStatusDTO;
import com.example.masterwork.order.DTOs.OrderRequestDTO;
import com.example.masterwork.orderitem.DTOs.OrderItemDTO;
import com.example.masterwork.orderitem.DTOs.OrderItemRequestDTO;
import com.example.masterwork.user.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.masterwork.order.utils.OrderStatus.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          StandardCharsets.UTF_8);
  private User user1;
  private User user2;
  private User emptyOrderListUser;
  private User createNewOrderUser;
  private User setNewStatusUser;
  private User deleteOrderUser;
  private Authentication user1Auth;
  private Authentication user2Auth;
  private Authentication emptyOrderListUserAuth;
  private Authentication createNewOrderUserAuth;
  private Authentication setNewStatusUserAuth;
  private Authentication deleteOrderUserAuth;

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    user1 = new User(2, "", "", "Oli2",
            "", "", "", "");
    user2 = new User(3, "", "", "Oli3",
            "", "", "", "");
    emptyOrderListUser = new User(4, "", "", "emptyOrder",
            "", "", "", "");
    createNewOrderUser = new User(5, "", "", "createNewOrderUser",
            "", "", "", "");
    setNewStatusUser = new User(6, "", "", "setNewStatusUser",
            "", "", "", "");
    deleteOrderUser = new User(7, "", "", "deleteOrderUser",
            "", "", "", "");
    user1Auth = new UsernamePasswordAuthenticationToken(user1, null, null);
    user2Auth = new UsernamePasswordAuthenticationToken(user2, null, null);
    emptyOrderListUserAuth = new UsernamePasswordAuthenticationToken(emptyOrderListUser, null, null);
    createNewOrderUserAuth = new UsernamePasswordAuthenticationToken(createNewOrderUser, null, null);
    setNewStatusUserAuth = new UsernamePasswordAuthenticationToken(setNewStatusUser, null, null);
    deleteOrderUserAuth = new UsernamePasswordAuthenticationToken(deleteOrderUser, null, null);

  }

  @Test
  public void getOrderById_should_returnAnOrder_when_orderIdGiven() throws Exception {
    OrderDTO expectedOrder = new OrderDTO(1001, 3400, "budakeszi",
            ORDER_CONFIRMED, 1681065838L, new ArrayList<>());
    MvcResult result = mockMvc.perform(get("/orders/1001").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items").isArray())
            .andExpect(jsonPath("$.items", hasSize(2)))
            .andReturn();

    OrderDTO receiverOrder = mapper.readValue(result.getResponse().getContentAsString(), OrderDTO.class);

    assertEquals(expectedOrder.getId(), receiverOrder.getId());
    assertEquals(expectedOrder.getTotalAmount(), receiverOrder.getTotalAmount());
    assertEquals(expectedOrder.getDeliveryAddress(), receiverOrder.getDeliveryAddress());
    assertEquals(expectedOrder.getStatus(), receiverOrder.getStatus());
    assertEquals(expectedOrder.getCreated(), receiverOrder.getCreated());
  }

  @Test
  public void getOrderById_should_returnAnError_when_differentUsersOrderIdGiven() throws Exception {
    mockMvc.perform(get("/orders/1001").principal(user2Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.status", Matchers.is("error")))
            .andExpect(jsonPath("$.message", Matchers.is("Forbidden action")));
  }

  @Test
  public void getOrderById_should_returnAnError_when_OrderIdDoesNotExist() throws Exception {
    mockMvc.perform(get("/orders/0").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", Matchers.is("error")))
            .andExpect(jsonPath("$.message", Matchers.is("Id not found")));
  }

  @Test
  public void getAllOrderByStatus_should_returnAnOrderList_when_userHasOrder_givenStatusIsNull() throws Exception {
    mockMvc.perform(get("/orders").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders", hasSize(2)));
  }

  @Test
  public void getAllOrderByStatus_should_returnAnEmptyList_when_userDoesNotHaveOrder_givenStatusIsNull() throws Exception {
    mockMvc.perform(get("/orders").principal(emptyOrderListUserAuth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orders").isEmpty());
  }

  @Test
  public void getAllOrderByStatus_should_returnAnOrderList_when_ordersStatusEqualsTheGivenStatus() throws Exception {
    mockMvc.perform(get("/orders?status=ORDER_CONFIRMED").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders", hasSize(1)));
  }

  @Test
  public void getAllOrderByStatus_should_returnAnEmptyList_when_userDoesNotHaveOrderWithTheGivenStatus()
          throws Exception {
    mockMvc.perform(get("/orders?status=READY_FOR_DELIVERY").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders", hasSize(0)));
  }

  @Test
  public void createNewOrder_should_returnTheCreatedOrder_when_everyInputAreValid() throws Exception {
    OrderRequestDTO newOrder = new OrderRequestDTO(7, Arrays.asList(
            new OrderItemRequestDTO(5, 1),
            new OrderItemRequestDTO(11, 2)));
    MvcResult result = mockMvc.perform(post("/orders")
                    .principal(createNewOrderUserAuth)
                    .contentType(contentType)
                    .content(mapper.writeValueAsString(newOrder)))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalAmount", is(9500)))
            .andExpect(jsonPath("$.status", is(ORDER_CONFIRMED.toString())))
            .andExpect(jsonPath("$.items", hasSize(2)))
            .andReturn();

    OrderDTO expectedOrder = new OrderDTO(9500, ORDER_CONFIRMED, Arrays.asList(
            new OrderItemDTO(1, "Pizza Hut", "Margharita pizza", 1, 2500),
            new OrderItemDTO(2, "Pizza Hut", "BBQ Pizza", 2, 7000)));

    OrderDTO receivedOrder = mapper.readValue(result.getResponse().getContentAsString(), OrderDTO.class);

    assertEquals(expectedOrder.getItems(), receivedOrder.getItems());
  }

  @Test
  public void createNewOrder_should_returnAnError_when_restaurantIdDoesNotExist() throws Exception {
    OrderRequestDTO newOrder = new OrderRequestDTO(0, new ArrayList<>());
    mockMvc.perform(post("/orders")
                    .principal(createNewOrderUserAuth)
                    .contentType(contentType)
                    .content(mapper.writeValueAsString(newOrder)))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Id not found")));
  }

  @Test
  public void createNewOrder_should_returnAnError_when_orderItemsListEmpty() throws Exception {
    OrderRequestDTO newOrder = new OrderRequestDTO(7, new ArrayList<>());
    mockMvc.perform(post("/orders")
                    .principal(createNewOrderUserAuth)
                    .contentType(contentType)
                    .content(mapper.writeValueAsString(newOrder)))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Order items list cannot be empty!")));
  }

  @Test
  public void createNewOrder_should_returnAnError_when_restaurantDoesNotHaveGivenMenu() throws Exception {
    OrderRequestDTO newOrder = new OrderRequestDTO(7, Arrays.asList(
            new OrderItemRequestDTO(0, 1),
            new OrderItemRequestDTO(11, 2)));
    mockMvc.perform(post("/orders")
                    .principal(createNewOrderUserAuth)
                    .contentType(contentType)
                    .content(mapper.writeValueAsString(newOrder)))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Id not found")));
  }

  @Test
  public void setNewOrderStatus_should_returnTheOrderWithNewStatus() throws Exception {
    OrderNewStatusDTO newStatus = new OrderNewStatusDTO("READY_FOR_DELIVERY");
    mockMvc.perform(put("/orders/1004/status")
                    .principal(setNewStatusUserAuth)
                    .contentType(contentType)
                    .content(mapper.writeValueAsString(newStatus)))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1004)))
            .andExpect(jsonPath("$.totalAmount", is(3400)))
            .andExpect(jsonPath("$.status", is(READY_FOR_DELIVERY.toString())))
            .andExpect(jsonPath("$.items", hasSize(2)));
  }

  @Test
  public void setNewOrderStatus_should_returnAnError_whenGivenOrderDoesNotExist() throws Exception {
    OrderNewStatusDTO newStatus = new OrderNewStatusDTO("READY_FOR_DELIVERY");
    mockMvc.perform(put("/orders/0/status")
                    .principal(setNewStatusUserAuth)
                    .contentType(contentType)
                    .content(mapper.writeValueAsString(newStatus)))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Id not found")));
  }

  @Test
  public void setNewOrderStatus_should_returnAnError_whenGivenOrderBelongsToAnotherUser() throws Exception {
    OrderNewStatusDTO newStatus = new OrderNewStatusDTO("READY_FOR_DELIVERY");
    mockMvc.perform(put("/orders/1001/status")
                    .principal(setNewStatusUserAuth)
                    .contentType(contentType)
                    .content(mapper.writeValueAsString(newStatus)))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Forbidden action")));
  }

  @Test
  public void setNewOrderStatus_should_returnAnError_whenGivenStatusIsDoesNotExist() throws Exception {
    OrderNewStatusDTO newStatus = new OrderNewStatusDTO("WRONG_STATUS");
    mockMvc.perform(put("/orders/1004/status")
                    .principal(setNewStatusUserAuth)
                    .contentType(contentType)
                    .content(mapper.writeValueAsString(newStatus)))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("This status is does not exist!")));
  }


  @Test
  public void setNewOrderStatus_should_returnAnError_whenGivenStatusIsTheSameAsBefore() throws Exception {
    OrderNewStatusDTO newStatus = new OrderNewStatusDTO(String.valueOf(ORDER_CONFIRMED));
    mockMvc.perform(put("/orders/1005/status")
                    .principal(setNewStatusUserAuth)
                    .contentType(contentType)
                    .content(mapper.writeValueAsString(newStatus)))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("The status does not be the same!")));
  }

  @Test
  public void deleteOrderById_should_returnTheDeletedOrder() throws Exception {
    mockMvc.perform(delete("/orders/1007").principal(deleteOrderUserAuth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1007)))
            .andExpect(jsonPath("$.totalAmount", is(2100)))
            .andExpect(jsonPath("$.status", is(String.valueOf(DELETED))))
            .andExpect(jsonPath("$.items", hasSize(2)));
  }

  @Test
  public void deleteOrderById_should_returnAnError_when_GivenOrderDoesNotExist() throws Exception {
    mockMvc.perform(delete("/orders/0").principal(deleteOrderUserAuth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Id not found")));
  }

  @Test
  public void deleteOrderById_should_returnAnError_when_GivenOrderBelongsToAnotherUser() throws Exception {
    mockMvc.perform(delete("/orders/1001").principal(deleteOrderUserAuth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Forbidden action")));
  }

}