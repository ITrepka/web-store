package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDto {
    private Integer id;
    private Integer userId;
    private List<Integer> productsIds;
    private Integer deliveryTypeId;
    private Integer promoCodeId;
    private BigDecimal orderPrice;
    private Integer orderStatusId;
    private Integer personalDataId;
    private Integer paymentTypeId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}