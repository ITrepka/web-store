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
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @ManyToOne
    private Category category;
}
