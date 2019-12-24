package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.Tag;
import pl.pretkejshop.webstore.service.dto.CreateUpdateTagDto;
import pl.pretkejshop.webstore.service.dto.TagDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagDtoMapper {
    public TagDto toDto(Tag tag) {
        List<Integer> productsIds = tag.getProducts() == null ? null :
                tag.getProducts().stream().map(Product::getId).collect(Collectors.toList());
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .createdAt(tag.getCreatedAt())
                .updatedAt(tag.getUpdatedAt())
                .productsIds(productsIds)
                .build();
    }

    public Tag toModel(CreateUpdateTagDto createTagDto) {
        return Tag.builder()
                .id(null)
                .name(createTagDto.getName())
                .createdAt(null)
                .updatedAt(null)
                .products(null)
                .build();
    }
}
