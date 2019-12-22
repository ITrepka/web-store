package pl.pretkejshop.webstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order_status")
public class OrderStatusController {
    @Autowired
    OrderStatusService orderStatusService;

    @GetMapping
    public List<OrderStatusDto> getAllOrderStatuss() {
        return orderStatusService.getAllOrderStatuss();
    }

    @GetMapping("/{id}")
    public OrderStatusDto getOrderStatusById(@PathVariable int id) throws NotFoundException {
        return orderStatusService.getOrderStatusById(id);
    }

    @PostMapping
    public OrderStatusDto addNewOrderStatus(@RequestBody CreateUpdateOrderStatusDto createOrderStatusDto) throws AlreadyExistsException, InvalidDataException {
        return orderStatusService.addNewOrderStatus(createOrderStatusDto);
    }

    @PutMapping("/{id}")
    public OrderStatusDto updateOrderStatusById(@PathVariable int id, @RequestBody CreateUpdateOrderStatusDto orderStatusToUpdate) throws NotFoundException, InvalidDataException {
        return orderStatusService.updateOrderStatusById(id, orderStatusToUpdate);
    }

    @DeleteMapping("/{id}")
    public OrderStatusDto deleteOrderStatusById(@PathVariable int id) throws NotFoundException {
        return orderStatusService.deleteOrderStatusById(id);
    }
}
