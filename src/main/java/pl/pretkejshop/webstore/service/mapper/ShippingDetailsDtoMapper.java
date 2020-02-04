package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Component;
import pl.pretkejshop.webstore.model.ShippingDetails;
import pl.pretkejshop.webstore.service.dto.CreateUpdateShippingDetailsDto;
import pl.pretkejshop.webstore.service.dto.ShippingDetailsDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

@Component
public class ShippingDetailsDtoMapper {
    public ShippingDetailsDto toDto(ShippingDetails shippingDetails) {
        Integer orderId = shippingDetails.getOrder() == null ? null : shippingDetails.getOrder().getId();
        return ShippingDetailsDto.builder()
                .id(shippingDetails.getId())
                .orderId(orderId)
                .name(shippingDetails.getName())
                .surname(shippingDetails.getSurname())
                .address(shippingDetails.getAddress())
                .phoneNumber(shippingDetails.getPhoneNumber())
                .createdAt(shippingDetails.getCreatedAt())
                .updatedAt(shippingDetails.getUpdatedAt())
                .build();
    }

    public ShippingDetails toModel(CreateUpdateShippingDetailsDto createShippingDetailsDto) throws NotFoundException {
        return ShippingDetails.builder()
                .id(null)
                .createdAt(null)
                .updatedAt(null)
                .name(createShippingDetailsDto.getName())
                .surname(createShippingDetailsDto.getSurname())
                .address(createShippingDetailsDto.getAddress())
                .phoneNumber(createShippingDetailsDto.getPhoneNumber())
                .order(null)
                .build();
    }
}
