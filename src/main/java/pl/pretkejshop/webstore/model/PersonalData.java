package pl.pretkejshop.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PersonalData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String surname;
    private String address;
    private String email;
    private LocalDate birthDate;
    private Sex sex;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
