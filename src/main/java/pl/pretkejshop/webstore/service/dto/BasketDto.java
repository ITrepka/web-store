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
public class BasketDto {
    private Integer id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Integer userId;
    private List<Integer> productsIds;
}
