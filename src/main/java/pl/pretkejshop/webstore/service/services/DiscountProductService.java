package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Discount;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.repository.DiscountRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.DiscountDtoMapper;
import pl.pretkejshop.webstore.service.mapper.ProductDtoMappper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountProductService {
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDtoMappper productDtoMappper;

    public List<ProductDto> getProductsWithDiscount(Integer discountId) throws NotFoundException {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new NotFoundException("Not Found Discount with id = " + discountId));
        return discount.getProducts() == null ? null : discount.getProducts().stream()
                .map(product -> productDtoMappper.toDto(product))
                .collect(Collectors.toList());
    }

    public ProductDto addDiscountToProduct(Integer productId, Integer discountId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Not Found Product with id = " + productId));
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new NotFoundException("Not Found Discount with id = " + discountId));

        discount.getProducts().add(product);
        product.setDiscount(discount);

        Product savedProduct = productRepository.save(product);
        return productDtoMappper.toDto(savedProduct);
    }

    public ProductDto deleteDiscountFromProduct(Integer productId, Integer discountId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Not Found Product with id = " + productId));
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new NotFoundException("Not Found Discount with id = " + discountId));

        discount.getProducts().remove(product);
        product.setDiscount(null);

        discountRepository.save(discount);
        Product savedProduct = productRepository.save(product);
        return productDtoMappper.toDto(savedProduct);
    }
}
