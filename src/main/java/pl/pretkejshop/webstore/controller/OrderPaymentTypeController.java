package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.OrderPaymentTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderPaymentTypeController {
    @Autowired
    private OrderPaymentTypeService paymentTypeOrderService;

    @GetMapping("/paymentTypes/{paymentTypeId}/orders")
    public List<OrderDto> getAllOrdersWithPaymentType(@PathVariable Integer paymentTypeId) throws NotFoundException {
        return paymentTypeOrderService.getAllOrdersWithPaymentType(paymentTypeId);
    }

    @PostMapping("/orders/{orderId}/paymentTypes/{paymentTypeId}")
    private OrderDto saveStatusForOrder(@PathVariable Integer orderId, @PathVariable Integer paymentTypeId) throws NotFoundException {
        return paymentTypeOrderService.saveOrderPaymentType(orderId, paymentTypeId);
    }

    @DeleteMapping("/orders/{orderId}/paymentTypes/{paymentTypeId}")
    private OrderDto deleteStatusFromOrder(@PathVariable Integer orderId, @PathVariable Integer paymentTypeId) throws NotFoundException {
        return paymentTypeOrderService.deleteOrderPaymentType(orderId, paymentTypeId);
    }
}
