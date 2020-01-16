package pl.pretkejshop.webstore.view.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopPageViewModel {
    private Integer pageNumber;
    private Integer amountOfPages;
    private Integer numberOfProductsOnTheSite;
    private String paragraph1;
    private List<ProductViewDto> ourProductsSample;
    private List<ProductViewDto> topRatedProducts;
    private List<ProductViewDto> productsMainView;
}
