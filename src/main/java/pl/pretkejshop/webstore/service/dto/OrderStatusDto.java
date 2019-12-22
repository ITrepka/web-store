package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.model.OrderStatusEnum;

import javax.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderStatusDto {
    private Integer id;
    private OrderStatusEnum status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<Integer> ordersIds;
}
