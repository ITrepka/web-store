package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Ad;
import pl.pretkejshop.webstore.service.dto.AdDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateAdDto;

@Service
public class AdDtoMapper {
    public AdDto toDto(Ad ad) {
        return AdDto.builder()
                .id(ad.getId())
                .price(ad.getPrice())
                .title(ad.getTitle())
                .text(ad.getText())
                .createdAt(ad.getCreatedAt())
                .updatedAt(ad.getUpdatedAt())
                .terminateDate(ad.getTerminateDate())
                .build();
    }

    public Ad toModel(CreateUpdateAdDto createAdDto) {
        return Ad.builder()
                .id(null)
                .price(createAdDto.getPrice())
                .title(createAdDto.getTitle())
                .text(createAdDto.getText())
                .createdAt(null)
                .updatedAt(null)
                .terminateDate(null)
                .build();
    }
}
