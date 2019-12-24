package pl.pretkejshop.webstore.model;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "baskets", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Product> products;
}
