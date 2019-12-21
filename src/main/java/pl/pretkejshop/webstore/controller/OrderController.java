package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateOrderPersonalDataDto;
import pl.pretkejshop.webstore.service.dto.CreateOrderUserDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.dto.UpdateOrderPersonalDataDto;
import pl.pretkejshop.webstore.service.exception.*;
import pl.pretkejshop.webstore.service.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable int id) throws OrderNotFoundException {
        return orderService.getOrderById(id);
    }

    @PostMapping("/user")
    public OrderDto addNewOrderByUser(@RequestBody CreateOrderUserDto createOrderUserDto) throws OrderInvalidDataException, CategoryNotFoundException, UserNotFoundException, ProductNotFoundException {
        return orderService.addNewOrder(createOrderUserDto);
    }

    @PostMapping("/personal-data")
    public OrderDto addNewUserByPersonalData (@RequestBody CreateOrderPersonalDataDto createOrderPersonalDataDto) throws PersonalDataNotFoundException {
        return orderService.addNewOrder(createOrderPersonalDataDto);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable int id, @RequestBody UpdateOrderPersonalDataDto orderToUpdate) throws OrderNotFoundException, OrderInvalidDataException, CategoryNotFoundException, PersonalDataNotFoundException {
        return orderService.updateOrder(id, orderToUpdate);
    }

    @DeleteMapping("/{id}")
    public OrderDto deleteOrder(@PathVariable int id) throws OrderNotFoundException {
        return orderService.deleteOrder(id);
    }
}
