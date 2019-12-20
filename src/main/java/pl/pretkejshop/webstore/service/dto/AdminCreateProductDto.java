package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminCreateProductDto {
    private String name;
    private String description;
    private Category category;
    private List<Tag> tagList;
    private Sex targetGender;
    private Photo photo;
    private BigDecimal sellingPrice;
    private BigDecimal boughtFor;
    private BigDecimal discount;
    private List<Rate> rates;
}
