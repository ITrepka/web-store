package pl.pretkejshop.webstore.view.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasketViewDto {
    private Map<ProductViewDto, Integer> productsInBasket = new HashMap<>();
    private BigDecimal priceForCartItems;
}
