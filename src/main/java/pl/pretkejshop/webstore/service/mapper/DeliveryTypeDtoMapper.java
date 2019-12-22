package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.DeliveryType;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.repository.DeliveryTypeRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateDeliveryTypeDto;
import pl.pretkejshop.webstore.service.dto.DeliveryTypeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryTypeDtoMapper {
    @Autowired
    private DeliveryTypeRepository deliveryTypeRepository;

    public DeliveryTypeDto toDto(DeliveryType deliveryType) {
        List<Integer> ordersIds = deliveryType.getOrders() == null ? null :
                deliveryType.getOrders().stream().map(Order::getId).collect(Collectors.toList());
        return DeliveryTypeDto.builder()
                .id(deliveryType.getId())
                .name(deliveryType.getName())
                .price(deliveryType.getPrice())
                .createdAt(deliveryType.getCreatedAt())
                .updatedAt(deliveryType.getUpdatedAt())
                .ordersIds(ordersIds)
                .build();
    }

    public DeliveryType toModel(CreateUpdateDeliveryTypeDto createDeliveryTypeDto) {
        return DeliveryType.builder()
                .id(null)
                .name(createDeliveryTypeDto.getName())
                .price(createDeliveryTypeDto.getPrice())
                .createdAt(null)
                .updatedAt(null)
                .orders(null)
                .build();
    }
}
