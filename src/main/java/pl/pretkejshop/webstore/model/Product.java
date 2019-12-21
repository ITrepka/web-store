package pl.pretkejshop.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Sex targetGender;
    private BigDecimal sellingPrice;
    private BigDecimal boughtFor;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Category category;
    @ManyToMany(mappedBy = "products")
    @Builder.Default
    private List<Tag> tagList = new ArrayList<>();
    @ManyToMany(mappedBy = "products")
    @Builder.Default
    private List<Photo> photos = new ArrayList<>();
    @ManyToMany(mappedBy = "products")
    @Builder.Default
    private List<Rate> rates = new ArrayList<>();
    @ManyToOne
    private Discount discount;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Basket basket;
}
