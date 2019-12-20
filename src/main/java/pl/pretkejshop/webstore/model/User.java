package pl.pretkejshop.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private PersonalData personalData;
    private String login;
    private String password;
    private Basket basket;
    private Integer loyaltyPoints;
    private List<Order> orders;
    private List<Message> messages;
    private Role role;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
