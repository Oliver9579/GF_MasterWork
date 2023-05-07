package com.example.masterwork.order.services;

import com.example.masterwork.exceptions.*;
import com.example.masterwork.menu.DTOs.MenuDTO;
import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.order.DTOs.OrderDTO;
import com.example.masterwork.order.DTOs.OrderDTOList;
import com.example.masterwork.order.DTOs.OrderNewStatusDTO;
import com.example.masterwork.order.DTOs.OrderRequestDTO;
import com.example.masterwork.order.models.Order;
import com.example.masterwork.order.repositories.OrderRepository;
import com.example.masterwork.order.utils.OrderStatus;
import com.example.masterwork.orderitem.DTOs.OrderItemRequestDTO;
import com.example.masterwork.orderitem.models.OrderItem;
import com.example.masterwork.orderitem.services.OrderItemService;
import com.example.masterwork.restaurant.models.Restaurant;
import com.example.masterwork.restaurant.services.RestaurantService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

  private OrderService orderService;

  @Mock
  private OrderRepository mockOrderRepository;
  @Mock
  private OrderItemService mockOrderItemService;
  @Mock
  private UserService mockUserService;
  @Mock
  private RestaurantService mockRestaurantService;

  private OrderDTO expectedOrder;
  private OrderDTO receivedOrder;
  private User user;
  private Restaurant restaurant;
  private List<Menu> menus;
  private Order newUserOrder;
  private Order savedOrder;
  private OrderRequestDTO requestDTO;
  private HashMap<Integer, Integer> quantities;
  private List<OrderItem> newOrderItems;

  @Before
  public void setup() {
    MockitoAnnotations.openMocks(this);
    this.orderService = Mockito.spy(new OrderServiceImpl(mockOrderRepository, mockOrderItemService,
            mockUserService, mockRestaurantService));
    expectedOrder = new OrderDTO();
    user = new User(1, "", "", "", "", "",
            "", "budakeszi");
    menus = Arrays.asList(new Menu(1, "sajtburger", "finom",
            1500, new ArrayList<>(), new ArrayList<>()));
    restaurant = new Restaurant(1, "McDonald's", "",
            "", new ArrayList<>(), new ArrayList<>());
    requestDTO = new OrderRequestDTO(restaurant.getId(), Arrays.asList(
            new OrderItemRequestDTO(1, 1)));
    quantities = new HashMap<Integer, Integer>() {{
      put(1, 1);
    }};
    newOrderItems = Arrays.asList(new OrderItem(1, 1500, menus.get(0)));
    newUserOrder = new Order(1500, "budakeszi",
            OrderStatus.ORDER_CONFIRMED, 1234567890L, user, restaurant);
    savedOrder = new Order(1, 1500, "budakeszi",
            OrderStatus.ORDER_CONFIRMED, 10L, user, restaurant);
  }

  @Test
  public void testGetOrderByIdWithMatchingUserId() {
    Integer orderId = 1;
    Integer userId = 2;
    Order order = new Order(2000, "budakeszi", OrderStatus.ORDER_CONFIRMED,
            0L, new User(), new Restaurant());
    expectedOrder = new OrderDTO(1, 2000, "budakeszi", OrderStatus.ORDER_CONFIRMED,
            0L, new ArrayList<>());
    when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
    doReturn(true).when(mockUserService).isUserIdMatching(userId, order);
    when(orderService.convertToOrderDTO(order, userId)).thenReturn(expectedOrder);

    receivedOrder = orderService.getOrderById(orderId, userId);

    assertEquals(expectedOrder.getTotalAmount(), receivedOrder.getTotalAmount());
    assertEquals(expectedOrder.getDeliveryAddress(), receivedOrder.getDeliveryAddress());
    assertEquals(expectedOrder.getStatus(), receivedOrder.getStatus());
  }

  @Test(expected = IdNotFoundException.class)
  public void testGetOrderByIdWithNonexistentOrderId() {
    Integer orderId = 1;
    Integer userId = 2;
    when(mockOrderRepository.findById(orderId)).thenThrow(IdNotFoundException.class);

    receivedOrder = orderService.getOrderById(orderId, userId);


  }

  @Test(expected = ForbiddenActionException.class)
  public void testGetOrderByIdWithNonMatchingUserId() {
    Integer orderId = 1;
    Integer userId = 2;
    Order order = new Order();
    when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
    doThrow(new ForbiddenActionException()).when(mockUserService).isUserIdMatching(userId, order);

    orderService.getOrderById(orderId, userId);

  }

  @Test
  public void testGetAllOrderById() {
    Integer userId = 1;
    List<Order> orders = Arrays.asList(new Order(), new Order());
    when(mockOrderRepository.findAllByUserId(userId)).thenReturn(orders);
    doReturn(new OrderDTO()).when(orderService).convertToOrderDTO(any(), eq(userId));

    OrderDTOList result = orderService.getAllOrderById(userId);

    assertNotNull(result);
    assertEquals(2, result.getOrders().size());
  }

  @Test
  public void testConvertToOrderDTO() {
    Integer userId = 1;
    Order order = new Order();
    OrderItem orderItem1 = new OrderItem();
    orderItem1.setPrice(10);
    OrderItem orderItem2 = new OrderItem();
    orderItem2.setPrice(20);
    order.setOrderItems(Arrays.asList(orderItem1, orderItem2));

    OrderDTO result = orderService.convertToOrderDTO(order, userId);

    assertNotNull(result);
    assertEquals(Integer.valueOf(30), result.getTotalAmount());
    assertEquals(2, result.getItems().size());
  }

  @Test
  public void testCreateNewOrder() {
    when(mockRestaurantService.getById(1)).thenReturn(restaurant);
    doReturn(menus).when(orderService).validateAndGetMenus(requestDTO, restaurant);
    doReturn(quantities).when(orderService).getQuantities(requestDTO);
    doReturn(newOrderItems).when(orderService).createOrderItems(menus, quantities);
    doReturn(newUserOrder).when(orderService).createOrder(user, restaurant, menus, newOrderItems);
    when(mockOrderRepository.save(newUserOrder)).thenReturn(savedOrder);
    when(mockOrderRepository.findById(1)).thenReturn(Optional.of(savedOrder));

    OrderDTO result = orderService.createNewOrder(new OrderRequestDTO(1,
            Arrays.asList(new OrderItemRequestDTO(1, 1))), user);

    expectedOrder = new OrderDTO(1, 1500, "budakeszi", OrderStatus.ORDER_CONFIRMED,
            10L, new ArrayList<>());

    assertEquals(expectedOrder.getId(), result.getId());
    assertEquals(expectedOrder.getTotalAmount(), result.getTotalAmount());
    assertEquals(expectedOrder.getStatus(), result.getStatus());
    verify(mockOrderRepository, times(1)).save(newUserOrder);
  }

  @Test(expected = MissingOrderItemsException.class)
  public void testCreateNewOrder_orderItemsEmpty() {
    OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
    orderRequestDTO.setRestaurantId(1);
    orderRequestDTO.setItems(Collections.emptyList());
    when(mockRestaurantService.getById(1)).thenReturn(restaurant);
    doThrow(MissingOrderItemsException.class).when(orderService).validateAndGetMenus(orderRequestDTO, restaurant);

    orderService.createNewOrder(orderRequestDTO, user);
    verify(mockOrderRepository, times(0)).save(newUserOrder);
  }

  @Test(expected = IdNotFoundException.class)
  public void testCreateNewOrderIdNotFound() {
    when(mockRestaurantService.getById(anyInt())).thenThrow(IdNotFoundException.class);

    orderService.createNewOrder(requestDTO, user);
    verify(mockOrderRepository, times(0)).save(newUserOrder);
  }

  @Test
  public void testValidateAndGetMenus_ValidOrder_ReturnsValidMenus() {
    List<OrderItemRequestDTO> orderItems = new ArrayList<>();
    orderItems.add(new OrderItemRequestDTO(1, 1));
    orderItems.add(new OrderItemRequestDTO(2, 1));
    OrderRequestDTO newOrder = new OrderRequestDTO(1, orderItems);

    List<Menu> menus = new ArrayList<>();
    menus.add(new Menu(1, "sajtburger", "finom", 1500, new ArrayList<>(), new ArrayList<>()));
    menus.add(new Menu(2, "big mac", "finom", 1500, new ArrayList<>(), new ArrayList<>()));
    Restaurant restaurant = new Restaurant(1, "burger king", "",
            "", new ArrayList<>(), menus);

    List<Menu> result = orderService.validateAndGetMenus(newOrder, restaurant);

    assertEquals(2, result.size());
    assertTrue(result.stream().anyMatch(menu -> menu.getId() == 1));
    assertTrue(result.stream().anyMatch(menu -> menu.getId() == 2));
  }

  @Test(expected = MissingOrderItemsException.class)
  public void testValidateAndGetMenus_MissingOrderItems_ThrowsException() {
    OrderRequestDTO newOrder = new OrderRequestDTO(1, new ArrayList<>());
    Restaurant restaurant = new Restaurant(1, "", "",
            "", new ArrayList<>(), new ArrayList<>());

    orderService.validateAndGetMenus(newOrder, restaurant);

  }

  @Test(expected = IdNotFoundException.class)
  public void testValidateAndGetMenus_InvalidOrderItem_ThrowsException() {
    List<OrderItemRequestDTO> orderItems = new ArrayList<>();
    orderItems.add(new OrderItemRequestDTO(1, 1));
    orderItems.add(new OrderItemRequestDTO(4, 1));
    OrderRequestDTO newOrder = new OrderRequestDTO(1, orderItems);

    List<Menu> menus = new ArrayList<>();
    menus.add(new Menu(1, "sajtburger", "finom", 1500, new ArrayList<>(), new ArrayList<>()));
    menus.add(new Menu(2, "big mac", "finom", 1500, new ArrayList<>(), new ArrayList<>()));
    Restaurant restaurant = new Restaurant(1, "", "", "", new ArrayList<>(), menus);

    orderService.validateAndGetMenus(newOrder, restaurant);
  }

  @Test(expected = IdNotFoundException.class)
  public void testValidateAndGetMenus_EmptyRestaurant_ThrowsException() {
    OrderRequestDTO newOrder = new OrderRequestDTO(1,
            Collections.singletonList(new OrderItemRequestDTO(1, 1)));
    Restaurant restaurant = new Restaurant(1, "", "",
            "", new ArrayList<>(), new ArrayList<>());

    orderService.validateAndGetMenus(newOrder, restaurant);
  }

  @Test
  public void testCreateOrderItems_ValidQuantities_ReturnsValidOrderItems() {
    List<Menu> menus = new ArrayList<>();
    menus.add(new Menu(1, "sajtburger", "finom", 1500, new ArrayList<>(), new ArrayList<>()));
    menus.add(new Menu(2, "big mac", "finom", 1500, new ArrayList<>(), new ArrayList<>()));

    HashMap<Integer, Integer> quantities = new HashMap<>();
    quantities.put(1, 2);
    quantities.put(2, 1);

    List<OrderItem> result = orderService.createOrderItems(menus, quantities);

    assertEquals(2, result.size());
    assertEquals(Integer.valueOf(2), result.get(0).getQuantity());
    assertEquals(Integer.valueOf(1), result.get(1).getQuantity());
    assertEquals(Integer.valueOf(3000), result.get(0).getPrice());
    assertEquals(Integer.valueOf(1500), result.get(1).getPrice());
    assertEquals(menus.get(0), result.get(0).getMenu());
    assertEquals(menus.get(1), result.get(1).getMenu());
  }

  @Test
  public void testCreateOrderItems_EmptyQuantities_ReturnsEmptyList() {
    List<Menu> menus = new ArrayList<>();
    menus.add(new Menu(1, "sajtburger", "finom", 1500, new ArrayList<>(), new ArrayList<>()));
    menus.add(new Menu(2, "big mac", "finom", 1500, new ArrayList<>(), new ArrayList<>()));

    HashMap<Integer, Integer> quantities = new HashMap<>();

    List<OrderItem> result = orderService.createOrderItems(menus, quantities);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testCreateOrder_ValidInput_ReturnsValidOrder() {
    Order result = orderService.createOrder(user, restaurant, menus, newOrderItems);

    assertEquals(OrderStatus.ORDER_CONFIRMED, result.getStatus());
    assertEquals(user.getAddress(), result.getDeliveryAddress());
    assertEquals(user, result.getUser());
    assertEquals(restaurant, result.getRestaurant());
  }

  @Test(expected = IdNotFoundException.class)
  public void testSetOrderNewStatus_IdNotFound() {
    OrderNewStatusDTO newOrderStatus = new OrderNewStatusDTO(OrderStatus.DELIVERED.toString());
    Integer orderId = 0;
    doThrow(IdNotFoundException.class).when(mockOrderRepository).findById(orderId);

    orderService.setOrderNewStatus(user, newOrderStatus, orderId);
    verify(mockOrderRepository, times(0)).save(any(Order.class));
  }

  @Test(expected = ForbiddenActionException.class)
  public void testSetOrderNewStatus_UserIdNotMatching() {
    User user2 = new User();
    user2.setId(2);
    OrderNewStatusDTO newOrderStatus = new OrderNewStatusDTO(OrderStatus.DELIVERED.toString());
    Integer orderId = 10;
    Order order = new Order();
    order.setId(orderId);
    order.setUser(user2);
    when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
    doThrow(ForbiddenActionException.class).when(mockUserService).isUserIdMatching(user.getId(), order);

    orderService.setOrderNewStatus(user, newOrderStatus, orderId);
  }

  @Test(expected = StatusDoesNotBeTheSameException.class)
  public void testSetOrderNewStatus_StatusNotChanged() {
    OrderNewStatusDTO newOrderStatus = new OrderNewStatusDTO(OrderStatus.ORDER_CONFIRMED.toString());
    Integer orderId = 10;
    Order order = new Order();
    order.setId(orderId);
    order.setStatus(OrderStatus.ORDER_CONFIRMED);
    order.setUser(user);
    when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
    doReturn(true).when(mockUserService).isUserIdMatching(user.getId(), order);

    orderService.setOrderNewStatus(user, newOrderStatus, orderId);
  }

  @Test
  public void testSetOrderNewStatus_Success() {
    OrderNewStatusDTO newOrderStatus = new OrderNewStatusDTO(OrderStatus.READY_FOR_DELIVERY.toString());
    Integer orderId = 10;
    Order order = new Order();
    order.setId(orderId);
    order.setStatus(OrderStatus.ORDER_CONFIRMED);
    order.setUser(user);
    when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
    doReturn(true).when(mockUserService).isUserIdMatching(user.getId(), order);
    when(mockOrderRepository.save(order)).thenReturn(order);

    OrderDTO expectedOrderDTO = new OrderDTO();
    expectedOrderDTO.setStatus(OrderStatus.READY_FOR_DELIVERY);

    OrderDTO actualOrderDTO = orderService.setOrderNewStatus(user, newOrderStatus, orderId);

    assertEquals(expectedOrderDTO.getStatus(), actualOrderDTO.getStatus());
  }

  @Test
  public void testGetOrdersByStatusValidStatusAndUserHasOrdersWithTheStatus() {
    Order order1 = new Order(10, "budakeszi", OrderStatus.ORDER_CONFIRMED,
            10L, user, null);
    Order order2 = new Order(20, "budakeszi", OrderStatus.READY_FOR_DELIVERY,
            10L, user, null);
    user.setOrders(Arrays.asList(order1, order2));
    String status = "READY_FOR_DELIVERY";

    OrderDTOList result = orderService.getOrdersByStatus(status, user);

    assertEquals(1, result.getOrders().size());
    assertTrue(result.getOrders().stream().anyMatch(order -> order.getId() == order2.getId()));
  }

  @Test(expected = StatusDoesNotExist.class)
  public void testGetOrdersByStatusInvalidStatus() {
    Order order1 = new Order(10, "budakeszi", OrderStatus.ORDER_CONFIRMED,
            10L, user, null);
    Order order2 = new Order(20, "budakeszi", OrderStatus.READY_FOR_DELIVERY,
            10L, user, null);
    user.setOrders(Arrays.asList(order1, order2));
    String status = "INVALID_STATUS";

    orderService.getOrdersByStatus(status, user);
  }

  @Test
  public void testDeleteOrderById_Success() {
    Integer orderId = 1;
    Order order1 = new Order(1, 10, "budakeszi", OrderStatus.ORDER_CONFIRMED,
            10L, user, null);
    when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order1));
    when(mockUserService.isUserIdMatching(user.getId(), order1)).thenReturn(true);

    OrderDTO result = orderService.deleteOrderById(user, orderId);

    assertNotNull(result);
    assertEquals(order1.getId(), result.getId());
    assertNotEquals(OrderStatus.ORDER_CONFIRMED, result.getStatus());
    assertEquals("DELETED", String.valueOf(result.getStatus()));

    verify(mockOrderRepository, times(1)).delete(order1);
  }

  @Test(expected = IdNotFoundException.class)
  public void testDeleteOrderById_IdNotFound() {
    Integer orderId = 0;
    when(mockOrderRepository.findById(orderId)).thenThrow(IdNotFoundException.class);

    orderService.deleteOrderById(user, orderId);

    verify(mockOrderRepository, times(0)).delete(any());
  }

  @Test(expected = ForbiddenActionException.class)
  public void testDeleteOrderById_IdNotBelongsToTheUser() {
    User user2 = new User(2, "", "", "", "", "", "", "");
    Integer orderId = 2;
    Order order = new Order(orderId, 0,"",OrderStatus.ORDER_CONFIRMED,
            0L, user2, new Restaurant(), new ArrayList<>());
    when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
    when(mockUserService.isUserIdMatching(user.getId(), order)).thenThrow(ForbiddenActionException.class);

    orderService.deleteOrderById(user, orderId);

    verify(mockOrderRepository, times(0)).delete(any());
  }

}
