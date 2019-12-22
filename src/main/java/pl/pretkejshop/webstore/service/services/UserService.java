package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pretkejshop.webstore.model.PersonalData;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.PersonalDataRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePersonalDataDto;
import pl.pretkejshop.webstore.service.dto.CreateUserDto;
import pl.pretkejshop.webstore.service.dto.UpdateUserDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.PersonalDataDtoMapper;
import pl.pretkejshop.webstore.service.mapper.UserDtoMapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDtoMapper userDtoMapper;
    @Autowired
    private PersonalDataDtoMapper personalDataDtoMapper;
    @Autowired
    private PersonalDataRepository personalDataRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userDtoMapper.toDto(user))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto getUserById(int id) throws NotFoundException {
        return userRepository.findById(id)
                .map(user -> userDtoMapper.toDto(user))
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + id));
    }

    @Transactional
    public UserDto addNewUser(CreateUserDto createUserDto) throws AlreadyExistsException, InvalidDataException {
        validateCreateUser(createUserDto);
        if (userRepository.existsByLogin(createUserDto.getLogin())) {
            throw new AlreadyExistsException("User with login = " + createUserDto.getLogin() + " already exists");
        }
        if (createUserDto.getCreatePersonalData() == null) {
            throw new InvalidDataException("personal data cannot be null") ;
        }
        if (personalDataRepository.existsByEmail(createUserDto.getCreatePersonalData().getEmail())) {
            throw new AlreadyExistsException("User with email = " + createUserDto.getCreatePersonalData().getEmail()
                    + " already exists");
        }
        PersonalData personalData = personalDataDtoMapper.toModel(createUserDto.getCreatePersonalData());
        PersonalData savedPersonalData = personalDataRepository.save(personalData);
        User user = userDtoMapper.toModel(createUserDto, savedPersonalData);
        user.setLoyaltyPoints(0);
        user.setCreatedAt(OffsetDateTime.now());
        //roleDoUstawienia todo
        //password set na zaszyfrowane todo
        user.setPassword(createUserDto.getPassword());
        User savedUser = userRepository.save(user);
        return userDtoMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto updateUserById(int id, UpdateUserDto userToUpdate) throws InvalidDataException, NotFoundException {
        validateUpdateUser(userToUpdate);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found User with id = " + id));
        CreateUpdatePersonalDataDto createUpdatePersonalDataDto = userToUpdate.getCreateUpdatePersonalDataDto();
        PersonalData personalData = personalDataDtoMapper.toModel(createUpdatePersonalDataDto);
        personalData.setId(user.getPersonalData().getId());
        user.setPersonalData(personalData);
        user.setPassword(userToUpdate.getPassword()); // todo szyfry
        user.setUpdatedAt(OffsetDateTime.now());
        personalData.setUser(user);
        PersonalData data = personalDataRepository.save(personalData);
        user.setPersonalData(data);
        User savedUser = userRepository.save(user);
        return userDtoMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto deleteUserById(int id) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + id));
        userRepository.deleteById(id);
        return userDtoMapper.toDto(user);
    }

    private void validateCreateUser(CreateUserDto createUserDto) throws InvalidDataException {
        if (createUserDto.getLogin() == null || createUserDto.getLogin().isEmpty()) {
            throw new InvalidDataException("User must have non-empty login");
        }
        if (createUserDto.getPassword() == null || createUserDto.getPassword().isEmpty()) {
            throw new InvalidDataException("User must have non-empty password");
        }
    }

    private void validateUpdateUser(UpdateUserDto userToUpdate) throws InvalidDataException {
        if (userToUpdate.getPassword() == null || userToUpdate.getPassword().isEmpty()) {
            throw new InvalidDataException("User must have non-empty password");
        }
    }
}
