package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderStatusDto;
import pl.pretkejshop.webstore.service.dto.OrderStatusDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.OrderStatusService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-status")
public class OrderStatusController {
    @Autowired
    private OrderStatusService orderStatusService;

    @GetMapping
    public List<OrderStatusDto> getAllOrderStatuss() {
        return orderStatusService.getAllOrderStatuss();
    }

    @GetMapping("/{id}")
    public OrderStatusDto getOrderStatusById(@PathVariable int id) throws NotFoundException {
        return orderStatusService.getOrderStatusById(id);
    }

    @PostMapping
    public OrderStatusDto addNewOrderStatus(@RequestBody CreateUpdateOrderStatusDto createOrderStatusDto) throws NotFoundException {
        return orderStatusService.addNewOrderStatus(createOrderStatusDto);
    }

    @PutMapping("/{id}")
    public OrderStatusDto updateOrderStatusById(@PathVariable int id, @RequestBody CreateUpdateOrderStatusDto orderStatusToUpdate) throws NotFoundException {
        return orderStatusService.updateOrderStatusById(id, orderStatusToUpdate);
    }

    @DeleteMapping("/{id}")
    public OrderStatusDto deleteOrderStatusById(@PathVariable int id) throws NotFoundException {
        return orderStatusService.deleteOrderStatusById(id);
    }
}
