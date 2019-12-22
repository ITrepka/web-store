package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.Product;

import javax.persistence.ManyToMany;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhotoDto {
    private Integer id;
    private String url;
    private String size;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<Integer> productsIds;
}
