package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.Product;

import javax.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscountDto {
    private Integer id;
    private String description;
    private Integer percentageValueReduction;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<Integer> productsIds;
}
