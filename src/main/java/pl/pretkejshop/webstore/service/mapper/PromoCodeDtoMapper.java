package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.PromoCode;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePromoCodeDto;
import pl.pretkejshop.webstore.service.dto.PromoCodeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromoCodeDtoMapper {
    public PromoCodeDto toDto(PromoCode promoCode) {
        List<Integer> ordersIds = promoCode.getOrders() == null ? null :
                promoCode.getOrders().stream().map(Order::getId).collect(Collectors.toList());

        return PromoCodeDto.builder()
                .id(promoCode.getId())
                .name(promoCode.getName())
                .description(promoCode.getDescription())
                .discount(promoCode.getDiscount())
                .createdAt(promoCode.getCreatedAt())
                .updatedAt(promoCode.getUpdatedAt())
                .ordersIds(ordersIds)
                .build();
    }

    public PromoCode toModel(CreateUpdatePromoCodeDto createPromoCodeDto) {
        return PromoCode.builder()
                .id(null)
                .name(createPromoCodeDto.getName())
                .discount(createPromoCodeDto.getDiscount())
                .description(createPromoCodeDto.getDescription())
                .createdAt(null)
                .updatedAt(null)
                .orders(null)
                .build();
    }
}
