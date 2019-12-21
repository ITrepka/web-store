package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.PersonalData;
import pl.pretkejshop.webstore.repository.OrderRepository;
import pl.pretkejshop.webstore.repository.PersonalDataRepository;
import pl.pretkejshop.webstore.service.dto.CreateOrderPersonalDataDto;
import pl.pretkejshop.webstore.service.dto.CreateOrderUserDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.dto.UpdateOrderPersonalDataDto;
import pl.pretkejshop.webstore.service.exception.OrderNotFoundException;
import pl.pretkejshop.webstore.service.exception.PersonalDataNotFoundException;
import pl.pretkejshop.webstore.service.exception.ProductNotFoundException;
import pl.pretkejshop.webstore.service.exception.UserNotFoundException;
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
    private PersonalDataRepository personalDataRepository;

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
        .map(order -> orderDtoMapper.toDto(order))
        .collect(Collectors.toList());
    }

    public OrderDto getOrderById(int id) throws OrderNotFoundException {
        return orderRepository.findById(id)
                .map(order -> orderDtoMapper.toDto(order))
                .orElseThrow(OrderNotFoundException::new);
    }

    public OrderDto addNewOrder(CreateOrderUserDto createOrderUserDto) throws UserNotFoundException, ProductNotFoundException {
        Order order = orderDtoMapper.toModel(createOrderUserDto);
        order.setCreatedAt(OffsetDateTime.now());
        order.setOrderPrice(null); //todo
        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }

    public OrderDto addNewOrder(CreateOrderPersonalDataDto createOrderPersonalDataDto) throws PersonalDataNotFoundException {
        Order order = orderDtoMapper.toModel(createOrderPersonalDataDto);
        order.setCreatedAt(OffsetDateTime.now());
        order.setOrderPrice(null); //todo
        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }

    public OrderDto updateOrder(int id, UpdateOrderPersonalDataDto orderToUpdate) throws OrderNotFoundException, PersonalDataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        Integer personalDataId = orderToUpdate.getPersonalDataId();
        PersonalData personalData = personalDataId == null ? null :
                personalDataRepository.findById(personalDataId)
                        .orElseThrow(() -> new PersonalDataNotFoundException(""));
        order.setPersonalData(personalData);
        order.setUpdatedAt(OffsetDateTime.now());
        order.setOrderPrice(null); // todo
        Order savedOrder = orderRepository.save(order);
        return orderDtoMapper.toDto(savedOrder);
    }

    public OrderDto deleteOrder(int id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        orderRepository.deleteById(id);
        return orderDtoMapper.toDto(order);
    }
}
