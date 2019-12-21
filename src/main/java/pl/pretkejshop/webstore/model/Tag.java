package pl.pretkejshop.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @ManyToMany
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
