package pl.pretkejshop.webstore.view.service.dto;

import lombok.*;
import pl.pretkejshop.webstore.service.dto.MessageDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.dto.PersonalDataDto;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "userId")
public class UserViewDto {
    private Integer userId;
    private String name;
    private String surname;
    private String address;
    private String birthDate;
    private String sex;
    private String phoneNumber;
    private Integer loyaltyPoints;
    private List<OrderDto> orders;
}
