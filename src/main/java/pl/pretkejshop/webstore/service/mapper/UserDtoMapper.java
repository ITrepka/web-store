package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.PersonalData;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.service.dto.CreateUserDto;
import pl.pretkejshop.webstore.service.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDtoMapper {
    @Autowired
    PersonalDataDtoMapper personalDataDtoMapper;

    public UserDto toDto(User user) {
        Integer basketId = user.getBasket() == null ? null : user.getBasket().getId();
        Integer roleId = user.getRole() == null ? null : user.getRole().getId();
        List<Integer> sentMessagesIds = user.getSentMessages() == null ? null : user.getSentMessages().stream().map(message -> message.getId()).collect(Collectors.toList());
        List<Integer> ordersIds = user.getOrders() == null ? null : user.getOrders().stream().map(order -> order.getId()).collect(Collectors.toList());
        Integer personalDataId = user.getPersonalData() == null ? null : user.getPersonalData().getId();
        return UserDto.builder()
                .id(user.getId())
                .personalDataId(personalDataId)
                .email(user.getEmail())
                .basketId(basketId)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roleId(roleId)
                .loyaltyPoints(user.getLoyaltyPoints())
                .sentMessagesIds(sentMessagesIds)
                .ordersIds(ordersIds)
                .build();
    }

    public User toModel(CreateUserDto createUserDto) {
        return User.builder()
                .id(null)
                .personalData(null)
                .email(createUserDto.getEmail())
                .password(null) //todo
                .basket(null)
                .loyaltyPoints(null) //todo
                .orders(null)
                .sentMessages(null)
                .role(null) //todo
                .basket(null)
                .createdAt(null) //todo
                .updatedAt(null)
                .build();
    }
}
