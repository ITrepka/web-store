package pl.pretkejshop.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String login;
    private String password;
    private Integer loyaltyPoints;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @OneToOne(cascade = CascadeType.ALL)
    private PersonalData personalData;
    @OneToOne
    private Basket basket;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @OneToMany(mappedBy = "userFrom")
    private List<Message> sentMessages;
    @OneToMany(mappedBy = "userTo")
    private List<Message> receivedMessages;
    @ManyToOne
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Rate> rates;
}
