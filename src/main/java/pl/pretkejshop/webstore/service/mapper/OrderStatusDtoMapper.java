package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.OrderStatus;
import pl.pretkejshop.webstore.repository.OrderStatusRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderStatusDto;
import pl.pretkejshop.webstore.service.dto.OrderStatusDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusDtoMapper {
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public OrderStatusDto toDto(OrderStatus orderStatus) {
        List<Integer> ordersIds = orderStatus.getOrders() == null ? null :
                orderStatus.getOrders().stream().map(Order::getId).collect(Collectors.toList());
        return OrderStatusDto.builder()
                .id(orderStatus.getId())
                .status(orderStatus.getStatus())
                .createdAt(orderStatus.getCreatedAt())
                .updatedAt(orderStatus.getUpdatedAt())
                .ordersIds(ordersIds)
                .build();
    }

    public OrderStatus toModel(CreateUpdateOrderStatusDto createOrderStatusDto) {
        return OrderStatus.builder()
                .id(null)
                .status(createOrderStatusDto.getStatus())
                .createdAt(null)
                .updatedAt(null)
                .orders(null)
                .build();
    }
}
