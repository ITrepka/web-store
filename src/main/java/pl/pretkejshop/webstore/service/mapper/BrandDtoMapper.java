package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Brand;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.repository.BrandRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.service.dto.BrandDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateBrandDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandDtoMapper {
    @Autowired
    private ProductRepository productRepository;

    public BrandDto toDto(Brand brand) {
        List<Integer> productsIds = brand.getProducts() == null ? null :
                brand.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());
        return BrandDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .createdAt(brand.getCreatedAt())
                .updatedAt(brand.getUpdatedAt())
                .productsIds(productsIds)
                .build();
    }

    public Brand toModel(CreateUpdateBrandDto createBrandDto) {
        return Brand.builder()
                .name(createBrandDto.getName())
                .id(null)
                .createdAt(null)
                .updatedAt(null)
                .products(null)
                .build();
    }
}
