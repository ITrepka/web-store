package pl.pretkejshop.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
    private Category category;
    @ManyToMany
    private List<Tag> tagList;
    private Sex targetGender;
    @ManyToOne
    private Photo photo;
    private BigDecimal sellingPrice;
    private BigDecimal boughtFor;
    private BigDecimal discount;
    @OneToMany
    private List<Rate> rates;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
