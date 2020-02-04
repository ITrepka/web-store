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
public class
Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<SubCategory> subCategories = new ArrayList<>();
}
