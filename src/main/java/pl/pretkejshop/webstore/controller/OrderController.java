package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderPersonalDataDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderUserDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
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
    public OrderDto addNewOrderByUser(@RequestBody CreateUpdateOrderUserDto createUpdateOrderUserDto) throws InvalidDataException, NotFoundException {
        return orderService.addNewOrder(createUpdateOrderUserDto);
    }

    @PostMapping("/personal-data")
    public OrderDto addNewUserByPersonalData (@RequestBody CreateUpdateOrderPersonalDataDto createUpdateOrderPersonalDataDto) throws NotFoundException {
        return orderService.addNewOrder(createUpdateOrderPersonalDataDto);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable int id, @RequestBody CreateUpdateOrderPersonalDataDto orderToUpdate) throws NotFoundException, InvalidDataException {
        return orderService.updateOrder(id, orderToUpdate);
    }

    @DeleteMapping("/{id}")
    public OrderDto deleteOrder(@PathVariable int id) throws NotFoundException {
        return orderService.deleteOrder(id);
    }
}
