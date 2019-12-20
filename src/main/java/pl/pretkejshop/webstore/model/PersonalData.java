package pl.pretkejshop.webstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @OneToOne(mappedBy = "personalData")
    private User user;
    private String name;
    private String surname;
    private String address;
    private String email;
    private LocalDate birthDate;
    private Sex sex;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
