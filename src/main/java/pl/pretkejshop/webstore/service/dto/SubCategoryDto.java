package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubCategoryDto {
    private Integer id;
    private String name;
    private Integer categoryId;
    private List<Integer> productsIds;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
