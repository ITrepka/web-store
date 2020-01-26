package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.UserOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserOrderController {
    @Autowired
    private UserOrderService userOrderService;

    @GetMapping("/users/{userId}/orders")
    public List<OrderDto> getUserOrders(@PathVariable Integer userId) throws NotFoundException {
        return userOrderService.getUserOrders(userId);
    }

    @PostMapping("/users/{userId}/orders/{orderId}")
    private UserDto addOrderToUser(@PathVariable Integer userId, @PathVariable Integer orderId) throws NotFoundException {
        return userOrderService.addOrderToUser(userId, orderId);
    }

    @DeleteMapping("/users/{userId}/orders/{orderId}")
    private UserDto deleteOrderFromUser(@PathVariable Integer userId, @PathVariable Integer orderId) throws NotFoundException {
        return userOrderService.deleteOrderFromUser(userId, orderId);
    }
}
