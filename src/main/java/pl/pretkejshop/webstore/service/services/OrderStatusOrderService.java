package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.OrderStatus;
import pl.pretkejshop.webstore.repository.OrderRepository;
import pl.pretkejshop.webstore.repository.OrderStatusRepository;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.OrderDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private OrderDtoMapper orderDtoMapper;

    public List<OrderDto> getAllOrdersWithOrderStatus(Integer orderStatusId) throws NotFoundException {
        return orderStatusRepository.findById(orderStatusId)
                .orElseThrow(() -> new NotFoundException("Order with id = " + orderStatusId + "not found"))
                .getOrders().stream()
                .map(order -> orderDtoMapper.toDto(order))
                .collect(Collectors.toList());
    }

    public OrderDto saveStatusForOrder(Integer orderId, Integer orderStatusId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + orderId));
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId)
                .orElseThrow(() -> new NotFoundException("Order with id = " + orderStatusId + "not found"));

        order.setOrderStatus(orderStatus);
        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }

    public OrderDto deleteStatusFromOrder(Integer orderId, Integer orderStatusId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + orderId));
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId)
                .orElseThrow(() -> new NotFoundException("Order with id = " + orderStatusId + "not found"));

        orderStatus.getOrders().remove(order);
        order.setOrderStatus(null);

        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }
}
