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
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private BigDecimal discount;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @OneToMany(mappedBy = "promoCode")
    private List<Order> orders;
}
