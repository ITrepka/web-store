package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pretkejshop.webstore.model.PersonalData;
import pl.pretkejshop.webstore.repository.PersonalDataRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePersonalDataDto;
import pl.pretkejshop.webstore.service.dto.PersonalDataDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.PersonalDataDtoMapper;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalDataService {
    @Autowired
    private PersonalDataRepository personalDataRepository;
    @Autowired
    private PersonalDataDtoMapper personalDataDtoMapper;

//    @PostConstruct
//    public void init() throws PersonalDataInvalidDataException {
//        addPersonalData(new CreateUpdatePersonalDataDto("Janek", "Zbyszkowski", "Zaokopowa 4, Brzeg", "dziadzior@ll.pl", LocalDate.now().toString(), null));
//        addPersonalData(new CreateUpdatePersonalDataDto("Włodzimierz", "Szaranowicz", "Barszcz 4, Katowice", "lloooll@ll.pl", LocalDate.now().toString(), null));
//    }

    @Transactional
    public List<PersonalDataDto> getAllPersonalData() {
        return personalDataRepository.findAll().stream()
                .map(personalData -> personalDataDtoMapper.toDto(personalData))
                .collect(Collectors.toList());
    }

    @Transactional
    public PersonalDataDto getPersonalDataById(int id) throws NotFoundException {
        return personalDataRepository.findById(id)
                .map(personalData -> personalDataDtoMapper.toDto(personalData))
                .orElseThrow(() -> new NotFoundException("Personal data with id = " + id + " not found"));
    }

    @Transactional
    public PersonalDataDto addPersonalData(CreateUpdatePersonalDataDto createPersonalDataDto) throws InvalidDataException {
        validCreateUpdatePersonalData(createPersonalDataDto);
        PersonalData personalData = personalDataDtoMapper.toModel(createPersonalDataDto);
        personalData.setCreatedAt(OffsetDateTime.now());
        PersonalData savedPersonalData = personalDataRepository.save(personalData);
        return personalDataDtoMapper.toDto(savedPersonalData);
    }

    @Transactional
    public PersonalDataDto updatePersonalData(int id, CreateUpdatePersonalDataDto updatePersonalDataDto) throws NotFoundException, InvalidDataException {
        validCreateUpdatePersonalData(updatePersonalDataDto);
        PersonalData personalData = personalDataRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Personal data with id = " + id + " not found"));
        personalData.setName(updatePersonalDataDto.getName());
        personalData.setSurname(updatePersonalDataDto.getSurname());
        personalData.setAddress(updatePersonalDataDto.getAddress());
        personalData.setBirthDate(LocalDate.parse(updatePersonalDataDto.getBirthDate()));
        personalData.setSex(updatePersonalDataDto.getSex());
        personalData.setUpdatedAt(OffsetDateTime.now());
        PersonalData savedPersonalData = personalDataRepository.save(personalData);
        return personalDataDtoMapper.toDto(savedPersonalData);
    }

    @Transactional
    public PersonalDataDto deletePersonalData(int id) throws NotFoundException {
        PersonalData personalData = personalDataRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Personal data with id = " + id + " not found"));
        personalDataRepository.delete(personalData);
        return personalDataDtoMapper.toDto(personalData);
    }

    private void validCreateUpdatePersonalData(CreateUpdatePersonalDataDto createPersonalDataDto) throws InvalidDataException {
        if (createPersonalDataDto.getAddress() == null || createPersonalDataDto.getName() == null || createPersonalDataDto.getBirthDate() == null ||
                createPersonalDataDto.getEmail().length() < 3 || !createPersonalDataDto.getEmail().contains("@")) {
            throw new InvalidDataException("valid personal date");
        }
    }
}
