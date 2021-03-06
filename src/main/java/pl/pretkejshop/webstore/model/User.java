package pl.pretkejshop.webstore.model;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String password;
    private Integer loyaltyPoints;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @OneToOne(cascade = CascadeType.ALL)
    private PersonalData personalData;
    @OneToOne
    private Basket basket;
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "userFrom", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Message> sentMessages = new ArrayList<>();
    @ManyToOne
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Rate> rates = new ArrayList<>();
}
