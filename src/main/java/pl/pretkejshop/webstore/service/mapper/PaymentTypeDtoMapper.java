package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.PaymentType;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePaymentTypeDto;
import pl.pretkejshop.webstore.service.dto.PaymentTypeDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentTypeDtoMapper {
    public PaymentTypeDto toDto(PaymentType paymentType) {
        List<Integer> ordersIds = paymentType.getOrders() == null ? null :
                paymentType.getOrders().stream().map(Order::getId).collect(Collectors.toList());
        return PaymentTypeDto.builder()
                .id(paymentType.getId())
                .paymentType(paymentType.getPaymentType())
                .createdAt(paymentType.getCreatedAt())
                .updatedAt(paymentType.getUpdatedAt())
                .ordersIds(ordersIds)
                .build();
    }

    public PaymentType toModel(CreateUpdatePaymentTypeDto createPaymentTypeDto) {
        return PaymentType.builder()
                .id(null)
                .paymentType(createPaymentTypeDto.getPaymentType())
                .createdAt(null)
                .updatedAt(null)
                .orders(null)
                .build();
    }
}
