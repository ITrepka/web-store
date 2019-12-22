package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Basket;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.BasketRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.BasketDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateBasketDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketDtoMapper {
    @Autowired
    private UserRepository userRepository;

    public BasketDto toDto(Basket basket) {
        Integer userId = basket.getUser() == null ? null : basket.getUser().getId();
        List<Integer> productsIds = basket.getProducts() == null ? null :
                basket.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());
        return BasketDto.builder()
                .id(basket.getId())
                .createdAt(basket.getCreatedAt())
                .updatedAt(basket.getUpdatedAt())
                .userId(userId)
                .productsIds(productsIds)
                .build();
    }

    public Basket toModel(CreateUpdateBasketDto createBasketDto) throws NotFoundException {
        Integer userId = createBasketDto.getUserId();
        User user = userId == null ? null :
                userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Not Found User with id = " + userId));

        return Basket.builder()
                .id(null)
                .createdAt(null)
                .updatedAt(null)
                .products(null)
                .user(user)
                .build();
    }
}
