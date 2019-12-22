package pl.pretkejshop.webstore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUpdateAdDto {
    private BigDecimal price;
    @NotBlank
    @Size(min = 3, message = "Title must be at least 3 chars long")
    private String title;
    @NotBlank
    @Size(min = 5, message = "Text must be at least 5 chars long")
    private String text;
}
