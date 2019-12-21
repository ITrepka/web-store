package pl.pretkejshop.webstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_from_id")
    private User userFrom;
    @ManyToOne
    @JoinColumn(name = "user_to_id")
    private User userTo;
}
