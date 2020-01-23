package pl.pretkejshop.webstore.view.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePersonalDataDto;
import pl.pretkejshop.webstore.service.dto.OrderDto;
import pl.pretkejshop.webstore.service.dto.PersonalDataDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.OrderService;
import pl.pretkejshop.webstore.service.services.PersonalDataService;
import pl.pretkejshop.webstore.view.service.dto.UserViewDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserViewDtoMapper {
    @Autowired
    private PersonalDataService personalDataService;
    @Autowired
    private OrderService orderService;

    public UserViewDto toDto(UserDto user) throws NotFoundException {
        Integer personalDataId = user.getPersonalDataId();
        PersonalDataDto personalData = personalDataId == null ? new PersonalDataDto() : personalDataService.getPersonalDataById(personalDataId);
        List<OrderDto> orderDtos = user.getOrdersIds() == null ? null : user.getOrdersIds().stream().map(id -> {
            try {
                return orderService.getOrderById(id);
            } catch (NotFoundException e) {
                throw new RuntimeException("UserViewDtoMapper");
            }
        }).collect(Collectors.toList());
        return UserViewDto.builder()
                .userId(user.getId())
                .name(personalData.getName())
                .surname(personalData.getSurname())
                .address(personalData.getAddress())
                .phoneNumber(personalData.getPhoneNumber())
                .birthDate(personalData.getBirthDate())
                .sex(personalData.getSex() == null ? null : String.valueOf(personalData.getSex()))
                .orders(orderDtos)
                .loyaltyPoints(user.getLoyaltyPoints())
                .build();
    }

    public CreateUpdatePersonalDataDto toPersonalDataDto(UserViewDto user) {
        return CreateUpdatePersonalDataDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate())
                .sex(user.getSex())
                .build();
    }
}
