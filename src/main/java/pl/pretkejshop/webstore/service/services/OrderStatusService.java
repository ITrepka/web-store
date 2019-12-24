package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.OrderStatus;
import pl.pretkejshop.webstore.repository.OrderStatusRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderStatusDto;
import pl.pretkejshop.webstore.service.dto.OrderStatusDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.OrderStatusDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusService {
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private OrderStatusDtoMapper orderStatusDtoMapper;

    public List<OrderStatusDto> getAllOrderStatuss() {
        return orderStatusRepository.findAll().stream()
                .map(orderStatus -> orderStatusDtoMapper.toDto(orderStatus))
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderStatusDto getOrderStatusById(int id) throws NotFoundException {
        return orderStatusRepository.findById(id)
                .map(orderStatus -> orderStatusDtoMapper.toDto(orderStatus))
                .orElseThrow(() -> new NotFoundException("Not found orderStatus with id = " + id));
    }

    @Transactional
    public OrderStatusDto addNewOrderStatus(CreateUpdateOrderStatusDto createOrderStatusDto) throws NotFoundException {
        OrderStatus orderStatus = orderStatusDtoMapper.toModel(createOrderStatusDto);
        orderStatus.setCreatedAt(OffsetDateTime.now());
        OrderStatus savedOrderStatus = orderStatusRepository.save(orderStatus);
        return orderStatusDtoMapper.toDto(savedOrderStatus);
    }

    @Transactional
    public OrderStatusDto updateOrderStatusById(int id, CreateUpdateOrderStatusDto orderStatusToUpdate) throws NotFoundException {
        OrderStatus orderStatus = orderStatusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found OrderStatus with id = " + id));

        orderStatus.setStatus(orderStatusToUpdate.getStatus());
        orderStatus.setUpdatedAt(OffsetDateTime.now());
        OrderStatus savedOrderStatus = orderStatusRepository.save(orderStatus);
        return orderStatusDtoMapper.toDto(savedOrderStatus);
    }

    @Transactional
    public OrderStatusDto deleteOrderStatusById(int id) throws NotFoundException {
        OrderStatus orderStatus = orderStatusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found orderStatus with id = " + id));
        orderStatusRepository.deleteById(id);
        return orderStatusDtoMapper.toDto(orderStatus);
    }
}
