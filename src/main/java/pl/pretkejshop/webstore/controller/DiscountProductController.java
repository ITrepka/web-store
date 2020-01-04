package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.model.Discount;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.DiscountProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DiscountProductController {
    @Autowired
    private DiscountProductService discountProductService;

    @GetMapping("/discounts/{discountId}/products")
    private List<ProductDto> getProductsWithDiscount(@PathVariable Integer discountId) throws NotFoundException {
        return discountProductService.getProductsWithDiscount(discountId);
    }

    @PostMapping("/products/{productId}/discounts/{discountId}")
    private ProductDto addDiscountToProduct(@PathVariable Integer productId, @PathVariable Integer discountId) throws NotFoundException {
        return discountProductService.addDiscountToProduct(productId, discountId);
    }

    @DeleteMapping("/products/{productId}/discounts/{discountId}")
    private ProductDto deleteDiscountFromProduct(@PathVariable Integer productId, @PathVariable Integer discountId) throws NotFoundException {
        return discountProductService.deleteDiscountFromProduct(productId, discountId);
    }
}
