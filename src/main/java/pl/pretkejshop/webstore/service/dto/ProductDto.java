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
    private String name;
    private String description;
    private Integer categoryId;
    private List<Integer> tagListIds;
    private String targetGender;
    private Integer photoId;
    private BigDecimal sellingPrice;
    private BigDecimal discount;
    private List<Integer> ratesIds;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
