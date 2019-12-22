package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubCategoryDto {
    private Long id;
    private String name;
    private Integer categoryId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
