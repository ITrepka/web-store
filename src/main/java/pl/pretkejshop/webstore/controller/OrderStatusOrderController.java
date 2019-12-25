package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.OrderStatusOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderStatusOrderController {
    @Autowired
    private OrderStatusOrderService orderStatusOrderService;

    @GetMapping("/orderStatus/{orderStatusId}/orders")
    public List<OrderDto> getAllOrdersWithOrderStatus(@PathVariable Integer orderStatusId) throws NotFoundException {
        return orderStatusOrderService.getAllOrdersWithOrderStatus(orderStatusId);
    }

    @PostMapping("/order/{orderId}/orderStatus/{orderStatusId}")
    private OrderDto saveStatusForOrder(@PathVariable Integer orderId, @PathVariable Integer orderStatusId) throws NotFoundException {
        return orderStatusOrderService.saveStatusForOrder(orderId, orderStatusId);
    }

    @DeleteMapping("/order/{orderId}/orderStatus/{orderStatusId}")
    private OrderDto deleteStatusFromOrder(@PathVariable Integer orderId, @PathVariable Integer orderStatusId) throws NotFoundException {
        return orderStatusOrderService.deleteStatusFromOrder(orderId, orderStatusId);
    }
}
