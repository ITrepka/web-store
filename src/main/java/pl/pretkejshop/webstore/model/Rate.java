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
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer rate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @OneToMany(mappedBy = "rate")
    private List<Comment> comments;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Product> products;
}
