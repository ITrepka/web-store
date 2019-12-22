package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.Order;

import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryTypeDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<Integer> ordersIds;
}
