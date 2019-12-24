package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.SubCategory;
import pl.pretkejshop.webstore.service.dto.CreateUpdateSubCategoryDto;
import pl.pretkejshop.webstore.service.dto.SubCategoryDto;

@Service
public class SubCategoryDtoMapper {
    public SubCategoryDto toDto(SubCategory subCategory) {
        Integer categoryId = subCategory.getCategory() == null ? null : subCategory.getCategory().getId();
        return SubCategoryDto.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .createdAt(subCategory.getCreatedAt())
                .updatedAt(subCategory.getUpdatedAt())
                .categoryId(categoryId)
                .build();
    }

    public SubCategory toModel(CreateUpdateSubCategoryDto createSubCategoryDto) {
        return SubCategory.builder()
                .id(null)
                .category(null)
                .name(createSubCategoryDto.getName())
                .createdAt(null)
                .updatedAt(null)
                .build();
    }
}
