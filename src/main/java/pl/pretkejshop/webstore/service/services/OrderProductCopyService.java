package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.ProductCopy;
import pl.pretkejshop.webstore.repository.OrderRepository;
import pl.pretkejshop.webstore.repository.ProductCopyRepository;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.dto.ProductCopyDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
//import pl.pretkejshop.webstore.service.mapper.OrderDtoMapper;
//import pl.pretkejshop.webstore.service.mapper.ProductCopyDtoMapper;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderProductCopyService {
//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private ProductCopyDtoMapper productCopyDtoMapper;
//    @Autowired
//    private ProductCopyRepository productCopyRepository;
//    @Autowired
//    private OrderDtoMapper orderDtoMapper;
//
//    public List<ProductCopyDto> getOrderProductCopies(Integer orderId) throws NotFoundException {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new NotFoundException("Not found order with id = " + orderId));
//        return order.getProductCopies().stream()
//                .map(productCopy -> productCopyDtoMapper.toDto(productCopy))
//                .collect(Collectors.toList());
//    }
//
//    public OrderDto addProductCopyToOrder(Integer orderId, Long productCopyId) throws NotFoundException {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new NotFoundException("Not found order with id = " + orderId));
//
//        ProductCopy productCopy= productCopyRepository.findById(productCopyId)
//                .orElseThrow(() -> new NotFoundException("Not found productCopy with id = " + productCopyId));
//
//        productCopy.setOrder(order);
//        order.getProductCopies().add(productCopy);
//        Order savedOrder = orderRepository.save(order);
//        return orderDtoMapper.toDto(savedOrder);
//    }
//
//
//    public OrderDto deleteProductCopyFromOrder(Integer orderId, Long productCopyId) throws NotFoundException {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new NotFoundException("Not found order with id = " + orderId));
//
//        ProductCopy productCopy= productCopyRepository.findById(productCopyId)
//                .orElseThrow(() -> new NotFoundException("Not found productCopy with id = " + productCopyId));
//
//        productCopy.setOrder(null);
//        order.getProductCopies().remove(productCopy);
//        Order savedOrder = orderRepository.save(order);
//        return orderDtoMapper.toDto(savedOrder);
//    }
//}
