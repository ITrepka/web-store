package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.PersonalData;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.PersonalDataRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateOrderPersonalDataDto;
import pl.pretkejshop.webstore.service.dto.CreateOrderUserDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.exception.PersonalDataNotFoundException;
import pl.pretkejshop.webstore.service.exception.ProductNotFoundException;
import pl.pretkejshop.webstore.service.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDtoMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PersonalDataRepository personalDataRepository;

    public OrderDto toDto(Order order) {
        Integer orderStatusId = order.getOrderStatus() == null ? null : order.getOrderStatus().getId();
        Integer paymentTypeId = order.getPaymentType() == null ? null : order.getPaymentType().getId();
        Integer deliveryTypeId = order.getDeliveryType() == null ? null : order.getDeliveryType().getId();
        Integer personalDataId = order.getPersonalData() == null ? null : order.getPersonalData().getId();
        Integer promoCodeId = order.getPromoCode() == null ? null : order.getPromoCode().getId();
        Integer userId = order.getUser() == null ? null : order.getUser().getId();
        List<Integer> productsIds = order.getProducts() == null ? null :
                order.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());

        return OrderDto.builder()
                .id(order.getId())
                .orderPrice(order.getOrderPrice())
                .deliveryTypeId(deliveryTypeId)
                .orderStatusId(orderStatusId)
                .paymentTypeId(paymentTypeId)
                .personalDataId(personalDataId)
                .promoCodeId(promoCodeId)
                .userId(userId)
                .productsIds(productsIds)
                .build();
    }

    public Order toModel(CreateOrderUserDto createOrderUserDto) throws UserNotFoundException, ProductNotFoundException {
        Integer userId = createOrderUserDto.getUserId();
        User user = userId == null ? null : userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Not found user with id = " + userId));

        PersonalData personalData = user == null ? null : user.getPersonalData();
        List<Product> products = createOrderUserDto.getProductsIds() == null ? null :
                createOrderUserDto.getProductsIds().stream()
                        .map(id -> productRepository.findById(id).orElse(null))
                        .collect(Collectors.toList());
        return Order.builder()
                .id(null)
                .orderPrice(null)
                .user(user)
                .personalData(personalData)
                .deliveryType(null)
                .promoCode(null)
                .orderStatus(null)
                .paymentType(null)
                .products(products)
                .build();
    }

    public Order toModel(CreateOrderPersonalDataDto createOrderPersonalDataDto) throws PersonalDataNotFoundException {
        List<Product> products = createOrderPersonalDataDto.getProductsIds() == null ? null :
                createOrderPersonalDataDto.getProductsIds().stream()
                        .map(id -> productRepository.findById(id).orElse(null))
                        .collect(Collectors.toList());
        Integer personalDataId = createOrderPersonalDataDto.getPersonalDataId();
        PersonalData personalData = personalDataId == null ? null :
                personalDataRepository.findById(personalDataId).orElseThrow(() -> new PersonalDataNotFoundException(""));
        return Order.builder()
                .id(null)
                .orderPrice(null)
                .user(null)
                .personalData(personalData)
                .deliveryType(null)
                .promoCode(null)
                .orderStatus(null)
                .paymentType(null)
                .products(products)
                .build();
    }
}
