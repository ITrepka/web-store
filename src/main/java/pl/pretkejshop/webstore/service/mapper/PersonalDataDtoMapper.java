package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.PersonalData;
import pl.pretkejshop.webstore.model.Sex;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePersonalDataDto;
import pl.pretkejshop.webstore.service.dto.PersonalDataDto;

import java.time.LocalDate;

@Service
public class PersonalDataDtoMapper {
    @Autowired
    private UserRepository userRepository;

    public PersonalDataDto toDto(PersonalData personalData) {
        User user = userRepository.findByPersonalData(personalData)
                .orElse(null);
        Integer userId = user == null ? null : user.getId();
        return PersonalDataDto.builder()
                .id(personalData.getId())
                .name(personalData.getName())
                .surname(personalData.getSurname())
                .address(personalData.getAddress())
                .sex(personalData.getSex())
                .birthDate(personalData.getBirthDate())
                .createdAt(personalData.getCreatedAt())
                .updatedAt(personalData.getUpdatedAt())
                .userId(userId)
                .phoneNumber(personalData.getPhoneNumber())
                .build();
    }

    public PersonalData toModel(CreateUpdatePersonalDataDto createPersonalDataDto) {
        return PersonalData.builder()
                .id(null)
                .name(createPersonalDataDto.getName())
                .surname(createPersonalDataDto.getSurname())
                .birthDate(null) //todo
                .sex(null) //todo
                .address(createPersonalDataDto.getAddress())
                .phoneNumber(createPersonalDataDto.getPhoneNumber())
                .createdAt(null)
                .updatedAt(null)
                .build();
    }
}
