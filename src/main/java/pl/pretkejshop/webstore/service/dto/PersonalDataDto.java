package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.Sex;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalDataDto {
    private Integer id;
    private String name;
    private String surname;
    private String address;
    private String email;
    private LocalDate birthDate;
    private Sex sex;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Integer userId;
    private String phoneNumber;
    private Integer orderId;
}
