package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Component;
import pl.pretkejshop.webstore.model.ShippingDetails;
import pl.pretkejshop.webstore.service.dto.CreateUpdateShippingDetailsDto;
import pl.pretkejshop.webstore.service.dto.ShippingDetailsDto;

@Component
public class ShippingDetailsDtoMapper {
    public ShippingDetailsDto toDto(ShippingDetails shippingDetails) {
        return ShippingDetailsDto.builder()
                .id(shippingDetails.getId())
                .name(shippingDetails.getName())
                .surname(shippingDetails.getSurname())
                .address(shippingDetails.getAddress())
                .phoneNumber(shippingDetails.getPhoneNumber())
                .createdAt(shippingDetails.getCreatedAt())
                .updatedAt(shippingDetails.getUpdatedAt())
                .build();
    }

    public ShippingDetails toModel(CreateUpdateShippingDetailsDto createShippingDetailsDto) {
        return ShippingDetails.builder()
                .id(null)
                .createdAt(null)
                .updatedAt(null)
                .name(createShippingDetailsDto.getName())
                .surname(createShippingDetailsDto.getSurname())
                .address(createShippingDetailsDto.getAddress())
                .phoneNumber(createShippingDetailsDto.getPhoneNumber())
                .build();
    }
}
