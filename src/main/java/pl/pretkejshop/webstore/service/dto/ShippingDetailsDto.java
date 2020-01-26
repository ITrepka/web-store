package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShippingDetailsDto {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
