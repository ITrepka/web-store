package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pretkejshop.webstore.model.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDto {
    private Integer id;
    private String text;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Integer userFromId;
    private Integer userToId;
}
