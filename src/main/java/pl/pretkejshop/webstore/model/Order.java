package pl.pretkejshop.webstore.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "product_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private BigDecimal orderPrice;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "order")
    private List<Product> products;
    @ManyToOne
    private DeliveryType deliveryType;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private PromoCode promoCode;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private OrderStatus orderStatus;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private PaymentType paymentType; //todo relation
}
