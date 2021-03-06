package pl.pretkejshop.webstore.view.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.pretkejshop.webstore.service.dto.BasketDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;
import pl.pretkejshop.webstore.view.service.dto.BasketViewDto;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BasketViewDtoMapper {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductViewDtoMapper productViewDtoMapper;

    public BasketViewDto toViewDto(BasketDto basketDto) throws NotFoundException {
        List<ProductViewDto> productsViewDtos = new ArrayList<>();
        if (basketDto.getProductsIds() != null) {
            for (Integer productId : basketDto.getProductsIds()) {
                ProductDto productDto = productService.getProductById(productId);
                productsViewDtos.add(productViewDtoMapper.toDto(productDto));
            }
        }
        Map<ProductViewDto, Integer> productsInBasket = new HashMap<>();

        productsViewDtos.stream().forEach(productViewDto -> productsInBasket.put(productViewDto,
                                productsInBasket.containsKey(productViewDto) ? productsInBasket.get(productViewDto) + 1 : 1));

        BigDecimal priceForCartItems = new BigDecimal(0);

        for (ProductViewDto productsViewDto : productsViewDtos) {
            priceForCartItems = priceForCartItems.add(productsViewDto.getSellingPrize());
        }

        return BasketViewDto.builder()
                .productsInBasket(productsInBasket)
                .priceForCartItems(priceForCartItems)
                .build();
    }
}
