package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.*;
import pl.pretkejshop.webstore.repository.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDtoMapper {
    @Autowired
    private ShippingDetailsRepository shippingDetailsRepository;
    @Autowired
    private ProductCopyRepository productCopyRepository;
    @Autowired
    private PromoCodeRepository promoCodeRepository;
    @Autowired
    private DeliveryTypeRepository deliveryTypeRepository;

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
        System.out.println(productsCopiesIds + "Product copies ids from dto mapper to DtoMethod");
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
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public Order toModel(CreateUpdateOrderDto createUpdateOrderDto) throws NotFoundException {
        Long shippingDetailsId = createUpdateOrderDto.getShippingDetailsId();
        ShippingDetails shippingDetails = shippingDetailsId == null ? null : shippingDetailsRepository.findById(shippingDetailsId)
                .orElseThrow(() -> new NotFoundException("Not found shipping details with id=" + shippingDetailsId));
        System.out.println("product Copies from orderDtoMapper " + createUpdateOrderDto.getProductsCopiesIds());
        List<ProductCopy> productsCopies = new ArrayList<>();
        for (Long productCopyId : createUpdateOrderDto.getProductsCopiesIds()) {
            ProductCopy productCopy = productCopyRepository.findById(productCopyId).orElseThrow(() -> new NotFoundException("Not found product copy with id = " + productCopyId));
            productsCopies.add(productCopy);
        }
        PromoCode promoCode = createUpdateOrderDto.getPromoCodeId() == null ? null :
                promoCodeRepository.findById(createUpdateOrderDto.getPromoCodeId())
                .orElseThrow(() -> new NotFoundException("Not found promo code with id = " + createUpdateOrderDto.getPromoCodeId()));
        Integer deliveryTypeId = createUpdateOrderDto.getDeliveryTypeId();
        DeliveryType deliveryType = deliveryTypeId == null ? null : deliveryTypeRepository.findById(deliveryTypeId)
                .orElseThrow(() -> new NotFoundException("Not Found Delivery Type with id = " + deliveryTypeId));
        return Order.builder()
                .id(null)
                .shippingDetails(shippingDetails)
                .orderPrice(null)
                .user(null)
                .deliveryType(deliveryType)
                .promoCode(promoCode)
                .orderStatus(null)
                .paymentType(null)
                .productCopies(productsCopies)
                .build();
    }
}
