package pl.pretkejshop.webstore.view.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.service.dto.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductViewDto {
    private Integer productId;
    private Integer numberOfCopies;
    private String name;
    private String description;
    private SubCategoryDto subCategoryDto;
    private List<TagDto> tagList;
    private String targetGender;
    private DiscountDto discount;
    private List<PhotoDto> photos;
    private BigDecimal sellingPrize;
    private List<RateDto> rates;
    private BrandDto brand;
    private Double averageRate;
    private OffsetDateTime createdAt;
}
