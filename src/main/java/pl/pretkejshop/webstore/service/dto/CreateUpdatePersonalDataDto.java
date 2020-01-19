package pl.pretkejshop.webstore.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.Sex;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUpdatePersonalDataDto {
    private String name;
    private String surname;
    private String address;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String birthDate;
    private String sex;
    private String phoneNumber;
}
