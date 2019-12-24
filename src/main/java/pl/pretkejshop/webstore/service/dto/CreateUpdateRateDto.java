package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.Comment;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUpdateRateDto {
    private Integer rate;
    private Integer commentId;
    private Integer userId;
}
