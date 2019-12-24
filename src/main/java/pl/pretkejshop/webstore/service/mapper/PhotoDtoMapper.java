package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Photo;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePhotoDto;
import pl.pretkejshop.webstore.service.dto.PhotoDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoDtoMapper {
    public PhotoDto toDto(Photo photo) {
        List<Integer> productsIds = photo.getProducts() == null ? null :
                photo.getProducts().stream().map(Product::getId).collect(Collectors.toList());
        return PhotoDto.builder()
                .id(photo.getId())
                .size(photo.getSize())
                .url(photo.getUrl())
                .createdAt(photo.getCreatedAt())
                .updatedAt(photo.getUpdatedAt())
                .productsIds(productsIds)
                .build();
    }

    public Photo toModel(CreateUpdatePhotoDto createPhotoDto) {
        return Photo.builder()
                .id(null)
                .size(createPhotoDto.getSize())
                .url(createPhotoDto.getUrl())
                .createdAt(null)
                .updatedAt(null)
                .products(null)
                .build();
    }
}
