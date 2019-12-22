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
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String paymentType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @OneToMany(mappedBy = "paymentType")
    private List<Order> orders;
}
