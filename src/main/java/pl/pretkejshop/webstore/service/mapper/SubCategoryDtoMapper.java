package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Category;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.SubCategory;
import pl.pretkejshop.webstore.repository.CategoryRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateSubCategoryDto;
import pl.pretkejshop.webstore.service.dto.SubCategoryDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubCategoryDtoMapper {
    @Autowired
    private CategoryRepository categoryRepository;

    public SubCategoryDto toDto(SubCategory subCategory) {
        List<Integer> productsIds = subCategory.getProducts() == null ? null :
                subCategory.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());
        Integer categoryId = subCategory.getCategory() == null ? null : subCategory.getCategory().getId();
        return SubCategoryDto.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .productsIds(productsIds)
                .createdAt(subCategory.getCreatedAt())
                .updatedAt(subCategory.getUpdatedAt())
                .categoryId(categoryId)
                .build();
    }

    public SubCategory toModel(CreateUpdateSubCategoryDto createSubCategoryDto) throws NotFoundException {
        Integer categoryId = createSubCategoryDto.getCategoryId();
        Category category = categoryId == null ? null : categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Not Found Category with id = " + categoryId));
        return SubCategory.builder()
                .id(null)
                .category(category)
                .name(createSubCategoryDto.getName())
                .products(null)
                .createdAt(null)
                .updatedAt(null)
                .build();
    }
}
