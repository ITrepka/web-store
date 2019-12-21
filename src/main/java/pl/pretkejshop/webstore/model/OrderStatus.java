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
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private OrderStatusEnum status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @OneToMany
    private List<Order> orders;
}
