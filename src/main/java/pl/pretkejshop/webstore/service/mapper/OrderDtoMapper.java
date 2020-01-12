package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.*;
import pl.pretkejshop.webstore.repository.ProductCopyRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateOrderUserDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDtoMapper {
    @Autowired
    private UserRepository userRepository;
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

        return OrderDto.builder()
                .id(order.getId())
                .orderPrice(order.getOrderPrice())
                .deliveryTypeId(deliveryTypeId)
                .orderStatusId(orderStatusId)
                .paymentTypeId(paymentTypeId)
                .promoCodeId(promoCodeId)
                .userId(userId)
                .productsCopiesIds(productsCopiesIds)
                .build();
    }

    public Order toModel(CreateUpdateOrderUserDto createUpdateOrderUserDto) throws NotFoundException {
        Integer userId = createUpdateOrderUserDto.getUserId();
        User user = userId == null ? null : userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));

        List<ProductCopy> productsCopies = createUpdateOrderUserDto.getProductsCopiesIds() == null ? null :
                createUpdateOrderUserDto.getProductsCopiesIds().stream()
                        .map(id -> productCopyRepository.findById(id).orElse(null))
                        .collect(Collectors.toList());
        return Order.builder()
                .id(null)
                .orderPrice(null)
                .user(user)
                .deliveryType(null)
                .promoCode(null)
                .orderStatus(null)
                .paymentType(null)
                .productCopies(productsCopies)
                .build();
    }
}
