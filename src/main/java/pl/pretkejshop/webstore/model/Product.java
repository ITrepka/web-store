package pl.pretkejshop.webstore.model;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToMany(mappedBy = "product")
    private List<ProductCopy> productCopies;
    private String name;
    private String description;
    private Sex targetGender;
    private BigDecimal sellingPrice;
    private BigDecimal boughtFor;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @ManyToOne()
    private Brand brand;
    @ManyToOne
    private Category category;
    @ManyToMany(mappedBy = "products")
    @Builder.Default
    private List<Tag> tagList = new ArrayList<>();
    @ManyToMany(mappedBy = "products", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Photo> photos = new ArrayList<>();
    @ManyToMany(mappedBy = "products", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    private List<Rate> rates = new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Discount discount;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Basket> baskets = new ArrayList<>();
}
