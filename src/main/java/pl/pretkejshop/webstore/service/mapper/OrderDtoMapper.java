package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.*;
import pl.pretkejshop.webstore.repository.ProductCopyRepository;
import pl.pretkejshop.webstore.repository.ShippingDetailsRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDtoMapper {
    @Autowired
    private ShippingDetailsRepository shippingDetailsRepository;
    @Autowired
    private ProductCopyRepository productCopyRepository;

    public OrderDto toDto(Order order) {
        Integer orderStatusId = order.getOrderStatus() == null ? null : order.getOrderStatus().getId();
        Integer paymentTypeId = order.getPaymentType() == null ? null : order.getPaymentType().getId();
        Integer deliveryTypeId = order.getDeliveryType() == null ? null : order.getDeliveryType().getId();
        Integer promoCodeId = order.getPromoCode() == null ? null : order.getPromoCode().getId();
        Integer userId = order.getUser() == null ? null : order.getUser().getId();
        List<Long> productsCopiesIds = order.getProductCopies() == null ? null :
                order.getProductCopies().stream()
                        .map(ProductCopy::getId)
                        .collect(Collectors.toList());
        Long shippingDetailsId = order.getShippingDetails() == null ? null : order.getShippingDetails().getId();

        return OrderDto.builder()
                .id(order.getId())
                .shippingDetailsId(shippingDetailsId)
                .orderPrice(order.getOrderPrice())
                .deliveryTypeId(deliveryTypeId)
                .orderStatusId(orderStatusId)
                .paymentTypeId(paymentTypeId)
                .promoCodeId(promoCodeId)
                .userId(userId)
                .productsCopiesIds(productsCopiesIds)
                .build();
    }

    public Order toModel(CreateUpdateOrderDto createUpdateOrderDto) throws NotFoundException {
        Long shippingDetailsId = createUpdateOrderDto.getShippingDetailsId();
        ShippingDetails shippingDetails = shippingDetailsId == null ? null : shippingDetailsRepository.findById(shippingDetailsId)
                .orElseThrow(() -> new NotFoundException("Not found shipping details with id=" + shippingDetailsId));
        List<ProductCopy> productsCopies = createUpdateOrderDto.getProductsCopiesIds() == null ? null :
                createUpdateOrderDto.getProductsCopiesIds().stream()
                        .map(id -> productCopyRepository.findById(id).orElse(null))
                        .collect(Collectors.toList());
        return Order.builder()
                .id(null)
                .shippingDetails(shippingDetails)
                .orderPrice(null)
                .user(null)
                .deliveryType(null)
                .promoCode(null)
                .orderStatus(null)
                .paymentType(null)
                .productCopies(productsCopies)
                .build();
    }
}
