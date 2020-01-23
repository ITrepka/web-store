package pl.pretkejshop.webstore.view.service.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.pretkejshop.webstore.service.dto.OrderDto;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String sex;
    private String phoneNumber;
    private Integer loyaltyPoints;
    private List<OrderDto> orders;
}
