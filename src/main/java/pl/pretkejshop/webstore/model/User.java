package pl.pretkejshop.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

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
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PersonalData personalData;
    private String login;
    private String password;
    @OneToOne(mappedBy = "user")
    private Basket basket;
    private Integer loyaltyPoints;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @OneToMany(mappedBy = "userFrom")
    private List<Message> sentMessages;
    @OneToMany(mappedBy = "userTo")
    private List<Message> receivedMessages;
    @ManyToOne
    private Role role;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
