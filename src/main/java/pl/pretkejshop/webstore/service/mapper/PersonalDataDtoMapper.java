package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.PersonalData;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePersonalDataDto;
import pl.pretkejshop.webstore.service.dto.PersonalDataDto;

import java.time.LocalDate;

@Service
public class PersonalDataDtoMapper {
    public PersonalDataDto toDto(PersonalData personalData) {
        return PersonalDataDto.builder()
                .id(personalData.getId())
                .name(personalData.getName())
                .surname(personalData.getSurname())
                .address(personalData.getAddress())
                .email(personalData.getEmail())
                .sex(personalData.getSex())
                .birthDate(personalData.getBirthDate())
                .createdAt(personalData.getCreatedAt())
                .updatedAt(personalData.getUpdatedAt())
                .build();
    }

    public PersonalData toModel(CreateUpdatePersonalDataDto createPersonalDataDto) {
        return PersonalData.builder()
                .id(null)
                .name(createPersonalDataDto.getName())
                .surname(createPersonalDataDto.getSurname())
                .email(createPersonalDataDto.getEmail())
                .birthDate(LocalDate.parse(createPersonalDataDto.getBirthDate()))
                .sex(createPersonalDataDto.getSex())
                .address(createPersonalDataDto.getAddress())
                .createdAt(null)
                .updatedAt(null)
                .build();
    }
}
