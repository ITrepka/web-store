package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.*;
import pl.pretkejshop.webstore.repository.BrandRepository;
import pl.pretkejshop.webstore.repository.CategoryRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateProductDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDtoMappper {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;

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
        Integer discountId = product.getDiscount() == null ? null : product.getDiscount().getId();
        List<Integer> basketsIds = product.getBaskets() == null ? null :
                product.getBaskets().stream().map(Basket::getId).collect(Collectors.toList());
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
                .discountId(discountId)
                .sellingPrice(product.getSellingPrice())
                .numberOfCopies(product.getNumberOfCopies())
                .basketsIds(basketsIds)
                .createdAt(null)
                .updatedAt(null)
                .build();
    }

    public Product toModel(CreateUpdateProductDto createProductDto) throws NotFoundException {
        Category category = createProductDto.getCategoryId() == null ? null :
                categoryRepository.findById(createProductDto.getCategoryId())
                        .orElseThrow(() -> new NotFoundException("Category with id = " + createProductDto.getCategoryId() + " not found"));
        Integer brandId = createProductDto.getBrandId();
        Brand brand = brandId == null ? null : brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand with id = " + brandId + " not found"));
        return Product.builder()
                .id(null)
                .name(createProductDto.getName())
                .description(createProductDto.getDescription())
                .boughtFor(null)
                .sellingPrice(createProductDto.getSellingPrice())
                .discount(null)
                .targetGender(createProductDto.getTargetGender())
                .category(category)
                .numberOfCopies(createProductDto.getNumberOfCopies())
                .brand(brand)
                .photos(null)
                .rates(null)
                .tagList(null)
                .createdAt(null)
                .updatedAt(null)
                .build();
    }
}
