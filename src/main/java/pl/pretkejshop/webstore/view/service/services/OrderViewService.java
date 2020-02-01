package pl.pretkejshop.webstore.view.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.PromoCode;
import pl.pretkejshop.webstore.service.dto.*;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.*;
import pl.pretkejshop.webstore.view.service.dto.BasketViewDto;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderViewService {
    @Autowired
    private DeliveryTypeService deliveryTypeService;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private ShippingDetailsService shippingDetailsService;
    @Autowired
    private PromoCodeService promoCodeService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PromoCodeOrderService promoCodeOrderService;
    @Autowired
    private OrderPaymentTypeService orderPaymentTypeService;
    @Autowired
    private ProductCopyService productCopyService;

    public List<DeliveryTypeDto> getDeliveryTypes() {
        return deliveryTypeService.getAllDeliveryTypes();
    }

    public List<PaymentTypeDto> getPaymentTypesList() {
        return paymentTypeService.getAllPaymentTypes();
    }

    public OrderDto submitTheOrder(CreateUpdateShippingDetailsDto createShippingDetailsDto, BasketViewDto currentCart, Integer deliveryTypeId,
                                   Integer paymentTypeId, String promoCode) throws NotFoundException {
        List<Long> productsCopiesIds = orderProductsFromCart(currentCart).stream()
                .map(ProductCopyDto::getId)
                .collect(Collectors.toList());
        PromoCodeDto promoCodeDto = promoCodeService.getPromoCodeByName(promoCode);
        ShippingDetailsDto shippingDetailsDto = shippingDetailsService.addNewShippingDetails(createShippingDetailsDto);
        CreateUpdateOrderDto createUpdateOrderDto = new CreateUpdateOrderDto(shippingDetailsDto.getId(),
                productsCopiesIds, deliveryTypeId);
        OrderDto orderDto = orderService.addNewOrder(createUpdateOrderDto);
        orderDto = promoCodeOrderService.addPromoCodeToOrder(orderDto.getId(), promoCodeDto.getId());
        orderDto = orderPaymentTypeService.saveOrderPaymentType(orderDto.getId(), paymentTypeId);
        return orderDto;
    }

    @Transactional
    public List<ProductCopyDto> orderProductsFromCart(BasketViewDto currentCart) throws NotFoundException {
        Map<ProductViewDto, Integer> productsInBasket = currentCart.getProductsInBasket();
        List<ProductCopyDto> productCopiesToOrder = new ArrayList<>();
        for (Map.Entry<ProductViewDto, Integer> entry : productsInBasket.entrySet()) {
            productCopiesToOrder.addAll(findProductCopies(entry));
        }
        return productCopiesToOrder;
    }

    private List<ProductCopyDto> findProductCopies(Map.Entry<ProductViewDto, Integer> entry) throws NotFoundException {
        ProductViewDto product = entry.getKey();
        Integer amount = entry.getValue();
        List<ProductCopyDto> productCopyDtos = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            ProductCopyDto productCopy = productCopyService.getFirstProductCopyByProductId(product.getProductId());
            productCopyDtos.add(productCopy);
        }
        return productCopyDtos;
    }
}
