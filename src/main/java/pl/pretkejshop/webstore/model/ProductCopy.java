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
public class ProductCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Order order;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
