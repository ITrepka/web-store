package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.*;
import pl.pretkejshop.webstore.repository.CategoryRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateProductDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.CategoryNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDtoMappper {
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductDto toDto(Product product) {
        Integer categoryId = product.getCategory() == null ? null : product.getCategory().getId();
        List <Integer> photoIds = product.getPhotos() == null ? null :
                product.getPhotos().stream()
                .map(Photo::getId)
                .collect(Collectors.toList());
        List<Integer> tagsIds = product.getTagList() == null ? null :
                product.getTagList().stream()
                        .map(Tag::getId)
                        .collect(Collectors.toList());
        List<Integer> ratesIds = product.getRates() == null ? null :
                product.getRates().stream()
                        .map(Rate::getId)
                        .collect(Collectors.toList());

        String targetGender = product.getTargetGender() == null ? null : String.valueOf(product.getTargetGender());
        Integer brandId = product.getBrand() == null ? null : product.getBrand().getId();
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryId(categoryId)
                .photoIds(photoIds)
                .tagListIds(tagsIds)
                .ratesIds(ratesIds)
                .brandId(brandId)
                .targetGender(targetGender)
                .discount(product.getDiscount())
                .sellingPrice(product.getSellingPrice())
                .createdAt(null)
                .updatedAt(null)
                .build();
    }

    public Product toModel(CreateUpdateProductDto createProductDto) throws CategoryNotFoundException {
        Category category = createProductDto.getCategoryId() == null ? null :
                categoryRepository.findById(createProductDto.getCategoryId())
                        .orElseThrow(CategoryNotFoundException::new);
        return Product.builder()
                .id(null)
                .name(createProductDto.getName())
                .description(createProductDto.getDescription())
                .boughtFor(null)
                .sellingPrice(createProductDto.getSellingPrice())
                .discount(createProductDto.getDiscount())
                .targetGender(createProductDto.getTargetGender())
                .category(category)
                .brand(null)
                .photos(null)
                .rates(null)
                .tagList(null)
                .createdAt(null)
                .updatedAt(null)
                .build();
    }
}
