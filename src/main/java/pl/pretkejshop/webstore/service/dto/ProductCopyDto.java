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
public class ProductCopyDto {
    private Long id;
    private Integer productId;
    private Integer orderId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
