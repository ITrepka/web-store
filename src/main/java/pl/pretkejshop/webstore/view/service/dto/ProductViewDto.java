package pl.pretkejshop.webstore.view.service.dto;

import lombok.*;
import pl.pretkejshop.webstore.service.dto.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "productId")
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
