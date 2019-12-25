package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.PaymentType;
import pl.pretkejshop.webstore.repository.OrderRepository;
import pl.pretkejshop.webstore.repository.PaymentTypeRepository;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.OrderDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderPaymentTypeService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private OrderDtoMapper orderDtoMapper;

    public List<OrderDto> getAllOrdersWithPaymentType(Integer paymentTypeId) throws NotFoundException {
        return paymentTypeRepository.findById(paymentTypeId)
                .orElseThrow(() -> new NotFoundException("Payment type with id = " + paymentTypeId + "not found"))
                .getOrders().stream()
                .map(order -> orderDtoMapper.toDto(order))
                .collect(Collectors.toList());
    }

    public OrderDto saveOrderPaymentType(Integer orderId, Integer paymentTypeId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + orderId));
        PaymentType paymentType = paymentTypeRepository.findById(paymentTypeId)
                .orElseThrow(() -> new NotFoundException("Payment Type with id = " + paymentTypeId + "not found"));

        order.setPaymentType(paymentType);
        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }

    public OrderDto deleteOrderPaymentType(Integer orderId, Integer paymentTypeId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order with id = " + orderId));
        PaymentType paymentType = paymentTypeRepository.findById(paymentTypeId)
                .orElseThrow(() -> new NotFoundException("Payment Type with id = " + paymentTypeId + "not found"));

        paymentType.getOrders().remove(order);
        order.setPaymentType(null);

        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }
}
