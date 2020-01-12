package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.ProductCopy;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateProductCopyDto;
import pl.pretkejshop.webstore.service.dto.ProductCopyDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

@Component
public class ProductCopyDtoMapper {
    @Autowired
    private ProductRepository productRepository;

    public ProductCopyDto toDto(ProductCopy productCopy) {
        Integer orderId = productCopy.getOrder() == null ? null : productCopy.getOrder().getId();
        Integer productId = productCopy.getProduct() == null ? null : productCopy.getProduct().getId();
        return ProductCopyDto.builder()
                .id(productCopy.getId())
                .createdAt(productCopy.getCreatedAt())
                .updatedAt(productCopy.getUpdatedAt())
                .orderId(orderId)
                .productId(productId)
                .build();
    }

    public ProductCopy toModel(CreateUpdateProductCopyDto createProductCopyDto) throws NotFoundException {
        Integer productId = createProductCopyDto.getProductId();
        Product product = productId == null ? null :
                productRepository.findById(productId)
                        .orElseThrow(() -> new NotFoundException("Not found product with id =" + productId));

        return ProductCopy.builder()
                .id(null)
                .createdAt(null)
                .updatedAt(null)
                .order(null)
                .product(product)
                .build();
    }
}
