package pl.pretkejshop.webstore.view.service.dto;

import lombok.*;
import pl.pretkejshop.webstore.service.dto.MessageDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.dto.PersonalDataDto;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "userId")
public class UserViewDto {
    private Integer userId;
    private PersonalDataDto personalDataDto;
    private Integer loyaltyPoints;
    private List<OrderDto> orders;
}
