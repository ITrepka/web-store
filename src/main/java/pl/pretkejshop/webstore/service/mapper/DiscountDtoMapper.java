package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Discount;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.repository.DiscountRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateDiscountDto;
import pl.pretkejshop.webstore.service.dto.DiscountDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountDtoMapper {
    @Autowired
    private DiscountRepository discountRepository;

    public DiscountDto toDto(Discount discount) {
        List<Integer> productsIds = discount.getProducts() == null ? null :
                discount.getProducts().stream().map(Product::getId).collect(Collectors.toList());
        return DiscountDto.builder()
                .id(discount.getId())
                .description(discount.getDescription())
                .createdAt(discount.getCreatedAt())
                .updatedAt(discount.getUpdatedAt())
                .percentageValueReduction(discount.getPercentageValueReduction())
                .productsIds(productsIds)
                .build();
    }

    public Discount toModel(CreateUpdateDiscountDto createDiscountDto) {
        return Discount.builder()
                .id(null)
                .description(createDiscountDto.getDescription())
                .percentageValueReduction(createDiscountDto.getPercentageValueReduction())
                .createdAt(null)
                .updatedAt(null)
                .products(null)
                .build();
    }
}
