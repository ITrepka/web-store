package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.*;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Integer id;
    private Integer personalDataId;
    private String email;
    private Integer basketId;
    private Integer loyaltyPoints;
    private List<Integer> ordersIds;
    private List<Integer> sentMessagesIds;
    private Integer roleId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
