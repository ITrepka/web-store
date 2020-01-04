package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.model.Basket;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.service.dto.BasketDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.BasketProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BasketProductController {
    @Autowired
    private BasketProductService basketProductService;

    @GetMapping("/baskets/{basketId}/products")
    private List<ProductDto> getBasketProducts(@PathVariable Integer basketId) throws NotFoundException {
        return basketProductService.getBasketProducts(basketId);
    }

    @PostMapping("/baskets/{basketId}/products/{productId}")
    private BasketDto addProductToBasket(@PathVariable Integer basketId, @PathVariable Integer productId) throws NotFoundException {
        return basketProductService.addProductToBasket(basketId, productId);
    }

    @DeleteMapping("/basket/{basketId}/products/{productId}")
    private BasketDto deleteProductFromBasket(@PathVariable Integer basketId, @PathVariable Integer productId) throws NotFoundException {
        return basketProductService.deleteProductFromBasket(basketId, productId);
    }
}
