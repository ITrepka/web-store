package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.ShippingDetails;
import pl.pretkejshop.webstore.repository.OrderRepository;
import pl.pretkejshop.webstore.repository.ShippingDetailsRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.dto.ShippingDetailsDto;
import pl.pretkejshop.webstore.service.mapper.OrderDtoMapper;
import pl.pretkejshop.webstore.service.mapper.ShippingDetailsDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
public class RelationsTestService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShippingDetailsRepository shippingDetailsRepository;
    @Autowired
    private ShippingDetailsDtoMapper shippingDetailsDtoMapper;
    @Autowired
    private OrderDtoMapper orderDtoMapper;

    @Transactional
    public String testOrderShippingDetailsRelation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dodanie Shipping Details<br>");
        ShippingDetails shippingDetails = new ShippingDetails(null, "Test", "Testowski",
                "Yelonky", "999000999", OffsetDateTime.now(), null, null);
        ShippingDetails savedSD = shippingDetailsRepository.save(shippingDetails);
        ShippingDetailsDto shippingDetailsDto = shippingDetailsDtoMapper.toDto(savedSD);
        sb.append("ShippingDetails po zapisaniu: ").append(shippingDetailsDto).append("<br>");

        sb.append("Dodanie zamówienia<br>");
        Order order = new Order(null, null, null, null, shippingDetails, null, null, null, null, null, null);
        Order savedOrder = orderRepository.save(order);
        OrderDto orderDto = orderDtoMapper.toDto(savedOrder);
        sb.append("Order po zapisaniu: ").append(orderDto).append("<br>");

        ShippingDetails shippingDetails1 = shippingDetailsRepository.findById(shippingDetailsDto.getId()).orElse(null);
        ShippingDetailsDto shippingDetailsDto2 = shippingDetailsDtoMapper.toDto(shippingDetails1);
        sb.append("ShippingDetails po zapisaniu ordera: ").append(shippingDetailsDto2).append("<br>");

        return sb.toString();
    }

}
