package pl.pretkejshop.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "product_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "order")
    private List<Product> products;
    @ManyToOne
    private DeliveryType deliveryType;
    @ManyToOne
    private PromoCode promoCode;
    private BigDecimal orderPrice;
    @ManyToOne
    private OrderStatus orderStatus;
    @OneToOne
    private PersonalData personalData;
    @ManyToOne
    private PaymentType paymentType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
