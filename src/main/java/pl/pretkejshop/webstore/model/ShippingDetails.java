package pl.pretkejshop.webstore.model;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class ShippingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "order_id")
    private Order order;
}
