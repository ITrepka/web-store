package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUpdateOrderUserDto {
    private Integer userId;
    private List<Integer> productsIds;
    private Integer deliveryTypeId;
    private Integer promoCodeId;
}
