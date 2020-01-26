package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.OrderRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.OrderDtoMapper;
import pl.pretkejshop.webstore.service.mapper.UserDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserOrderService {
    @Autowired
    private OrderDtoMapper orderDtoMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserDtoMapper userDtoMapper;

    public List<OrderDto> getUserOrders(Integer userId) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id=" + userId));
        return user.getOrders() == null ? null : user.getOrders().stream()
                .map(order -> orderDtoMapper.toDto(order))
                .collect(Collectors.toList());
    }

    public UserDto addOrderToUser(Integer userId, Integer orderId) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id=" + userId));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order with id=" + orderId));

        order.setUser(user);
        user.getOrders().add(order);
        User savedUser = userRepository.save(user);
        return userDtoMapper.toDto(savedUser);
    }

    public UserDto deleteOrderFromUser(Integer userId, Integer orderId) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id=" + userId));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Not found order with id=" + orderId));
        order.setUser(null);
        user.getOrders().remove(order);
        User updatedUser = userRepository.save(user);
        return userDtoMapper.toDto(updatedUser);
    }
}
