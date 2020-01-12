package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Integer id;
    private List<Long> productCopiesIds;
    private String name;
    private String description;
    private Integer categoryId;
    private List<Integer> tagListIds;
    private String targetGender;
    private List<Integer> photoIds;
    private BigDecimal sellingPrice;
    private Integer discountId;
    private List<Integer> ratesIds;
    private Integer brandId;
    private List<Integer> basketsIds;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
