package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.Comment;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.User;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RateDto {
    private Integer id;
    private Integer rate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<Integer> commentsIds;
    private Integer userId;
    private List<Integer> productsIds;
}
