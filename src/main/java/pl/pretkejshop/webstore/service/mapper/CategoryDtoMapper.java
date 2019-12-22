package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Category;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.SubCategory;
import pl.pretkejshop.webstore.repository.CategoryRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.repository.SubCategoryRepository;
import pl.pretkejshop.webstore.service.dto.CategoryDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryDtoMapper {
    public CategoryDto toDto(Category category) {
        List<Integer> productsIds = category.getProducts() == null ? null :
                category.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());
        List<Integer> categoriesIds = category.getSubCategories() == null ? null :
                category.getSubCategories().stream()
                        .map(SubCategory::getId)
                        .collect(Collectors.toList());
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .productsIds(productsIds)
                .subCategoryIds(categoriesIds)
                .build();
    }

    public Category toModel(CreateUpdateCategoryDto createCategoryDto) {
        return Category.builder()
                .id(null)
                .createdAt(null)
                .updatedAt(null)
                .products(null)
                .subCategories(null)
                .name(createCategoryDto.getName())
                .build();
    }
}
