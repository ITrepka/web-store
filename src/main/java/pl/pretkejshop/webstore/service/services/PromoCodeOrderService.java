package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.PromoCode;
import pl.pretkejshop.webstore.repository.OrderRepository;
import pl.pretkejshop.webstore.repository.PromoCodeRepository;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.OrderDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromoCodeOrderService {
    @Autowired
    private PromoCodeRepository promoCodeRepository;
    @Autowired
    private OrderDtoMapper orderDtoMapper;
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDto> getOrdersWithPromoCode(Integer promoCodeId) throws NotFoundException {
        return promoCodeRepository.findById(promoCodeId)
                .orElseThrow(() -> new NotFoundException("Order with id = " + promoCodeId + "not found"))
                .getOrders().stream()
                .map(order -> orderDtoMapper.toDto(order))
                .collect(Collectors.toList());
    }

    public OrderDto addPromoCodeToOrder(Integer orderId, Integer promoCodeId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with id = " + orderId + "not found"));
        PromoCode promoCode = promoCodeRepository.findById(promoCodeId)
                .orElseThrow(() -> new NotFoundException("PromoCode with id = " + promoCodeId + "not found"));

        promoCode.getOrders().add(order);
        order.setPromoCode(promoCode);

        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }

    public OrderDto deletePromoCodeFromOrder(Integer orderId, Integer promoCodeId) throws NotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with id = " + orderId + "not found"));
        PromoCode promoCode = promoCodeRepository.findById(promoCodeId)
                .orElseThrow(() -> new NotFoundException("PromoCode with id = " + promoCodeId + "not found"));

        promoCode.getOrders().remove(order);
        order.setPromoCode(null);

        Order changedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(changedOrder);
    }
}
