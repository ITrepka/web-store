package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.OrderStatusEnum;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUpdateOrderStatusDto {
    private OrderStatusEnum status;
}
