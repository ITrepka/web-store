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
        String role = user.getRole() == null ? null : String.valueOf(user.getRole());
        List<Integer> messagesIds = user.getMessages() == null ? null : user.getMessages().stream().map(message -> message.getId()).collect(Collectors.toList());
        List<Integer> ordersIds = user.getOrders() == null ? null : user.getOrders().stream().map(order -> order.getId()).collect(Collectors.toList());

        return UserDto.builder()
                .id(user.getId())
                .personalData(user.getPersonalData())
                .login(user.getLogin())
                .basketId(basketId)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .role(role)
                .loyaltyPoints(user.getLoyaltyPoints())
                .messagesIds(messagesIds)
                .ordersIds(ordersIds)
                .build();
    }

    public User toModel(CreateUserDto createUserDto) {
        PersonalData personalData = createUserDto.getCreatePersonalData() == null ? null :
                personalDataDtoMapper.toModel(createUserDto.getCreatePersonalData());
        return User.builder()
                .id(null)
                .personalData(personalData)
                .login(createUserDto.getLogin())
                .password(null) //todo
                .basket(null)
                .loyaltyPoints(null) //todo
                .orders(null)
                .messages(null)
                .role(null) //todo
                .createdAt(null) //todo
                .updatedAt(null)
                .build();
    }
}
