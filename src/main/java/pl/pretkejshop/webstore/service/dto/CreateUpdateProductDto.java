package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUpdateProductDto {
    private String name;
    private String description;
    private Integer categoryId;
    private Sex targetGender;
    private BigDecimal sellingPrice;
    private Integer brandId;
}
