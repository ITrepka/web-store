package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.*;
import pl.pretkejshop.webstore.repository.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.OrderDtoMapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderDtoMapper orderDtoMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DeliveryTypeRepository deliveryTypeRepository;
    @Autowired
    private PromoCodeRepository promoCodeRepository;
    @Autowired
    private ProductCopyRepository productCopyRepository;
    @Autowired
    private ShippingDetailsRepository shippingDetailsRepository;

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
        .map(order -> orderDtoMapper.toDto(order))
        .collect(Collectors.toList());
    }

    public OrderDto getOrderById(int id) throws NotFoundException {
        return orderRepository.findById(id)
                .map(order -> orderDtoMapper.toDto(order))
                .orElseThrow(() -> new NotFoundException("Order with id = " + id + " not found"));
    }

    public OrderDto addNewOrder(CreateUpdateOrderDto createUpdateOrderDto) throws NotFoundException {
        //todo validate
        Order order = orderDtoMapper.toModel(createUpdateOrderDto);
        order.setCreatedAt(OffsetDateTime.now());
        order.setOrderPrice(null); //todo
        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }

    public OrderDto updateOrder(int id, CreateUpdateOrderDto orderToUpdate) throws NotFoundException {
        //todo validate
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order with id = " + id + " not found"));
        Long shippingDetailsId = orderToUpdate.getShippingDetailsId();
        ShippingDetails shippingDetails = shippingDetailsId == null ? null : shippingDetailsRepository.findById(shippingDetailsId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + shippingDetailsId));
        order.setShippingDetails(shippingDetails);
        Integer deliveryTypeId = orderToUpdate.getDeliveryTypeId();
        DeliveryType deliveryType = deliveryTypeId == null ? null : deliveryTypeRepository.findById(deliveryTypeId)
                .orElseThrow(() -> new NotFoundException("Delivery Type not found id =" + deliveryTypeId));
        Integer promoCodeId = orderToUpdate.getPromoCodeId();
        PromoCode promoCode = promoCodeId == null ? null : promoCodeRepository.findById(promoCodeId)
                .orElseThrow(() -> new NotFoundException("Promo Code not found id =" + promoCodeId));
        List<Long> productsCopiesIds = orderToUpdate.getProductsCopiesIds();
        List<ProductCopy> productsCopies = productsCopiesIds == null ? null : productCopyRepository.findAllById(productsCopiesIds);
        order.setProductCopies(productsCopies);
        order.setPromoCode(promoCode);
        order.setDeliveryType(deliveryType);
        order.setUpdatedAt(OffsetDateTime.now());
        order.setOrderPrice(null); // todo
        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }

    public OrderDto deleteOrder(int id) throws NotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order with id = " + id + " not found"));
        orderRepository.deleteById(id);
        return orderDtoMapper.toDto(order);
    }
}
