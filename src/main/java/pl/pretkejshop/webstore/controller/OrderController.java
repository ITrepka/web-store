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
    public OrderDto getOrder(@PathVariable int id) throws NotFoundException {
        return orderService.getOrderById(id);
    }

    @PostMapping("/user")
    public OrderDto addNewOrderByUser(@RequestBody CreateOrderUserDto createOrderUserDto) throws InvalidDataException, NotFoundException {
        return orderService.addNewOrder(createOrderUserDto);
    }

    @PostMapping("/personal-data")
    public OrderDto addNewUserByPersonalData (@RequestBody CreateOrderPersonalDataDto createOrderPersonalDataDto) throws NotFoundException {
        return orderService.addNewOrder(createOrderPersonalDataDto);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable int id, @RequestBody UpdateOrderPersonalDataDto orderToUpdate) throws NotFoundException, InvalidDataException {
        return orderService.updateOrder(id, orderToUpdate);
    }

    @DeleteMapping("/{id}")
    public OrderDto deleteOrder(@PathVariable int id) throws NotFoundException {
        return orderService.deleteOrder(id);
    }
}
