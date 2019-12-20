package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePersonalDataDto;
import pl.pretkejshop.webstore.service.dto.CreateUserDto;
import pl.pretkejshop.webstore.service.dto.UpdateUserDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.UserAlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.UserInvalidDataException;
import pl.pretkejshop.webstore.service.exception.UserNotFoundException;
import pl.pretkejshop.webstore.service.mapper.PersonalDataDtoMapper;
import pl.pretkejshop.webstore.service.mapper.UserDtoMapper;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {

    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userDtoMapper.toDto(user))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto getUserById(int id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(user -> userDtoMapper.toDto(user))
                .orElseThrow(() -> new UserNotFoundException("Not found user with id = " + id));
    }

    @Transactional
    public UserDto addNewUser(CreateUserDto createUserDto) throws UserAlreadyExistsException, UserInvalidDataException {
        validateCreateUser(createUserDto);
        if (userRepository.existsByLogin(createUserDto.getLogin())) {
            throw new UserAlreadyExistsException("User with login = " + createUserDto.getLogin() + " already exists");
        }

        User user = userDtoMapper.toModel(createUserDto);
        user.setLoyaltyPoints(0);
        user.setCreatedAt(OffsetDateTime.now());
        //password set na zaszyfrowane todo
        user.setPassword(createUserDto.getPassword());
        User savedUser = userRepository.save(user);
        return userDtoMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto updateUserById(int id, UpdateUserDto userToUpdate) throws UserInvalidDataException, UserNotFoundException {
        validateUpdateUser(userToUpdate);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Not Found User with id = " + id));
        CreateUpdatePersonalDataDto createUpdatePersonalDataDto = userToUpdate.getCreateUpdatePersonalDataDto();
        user.setPersonalData(personalDataDtoMapper.toModel(createUpdatePersonalDataDto));
        user.setPassword(userToUpdate.getPassword()); // todo szyfry
        user.setUpdatedAt(OffsetDateTime.now());
        User savedUser = userRepository.save(user);
        return userDtoMapper.toDto(savedUser);
    }


    @Transactional
    public UserDto deleteUserById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Not found user with id = " + id));
        userRepository.delete(user);
        return userDtoMapper.toDto(user);
    }

    private void validateCreateUser(CreateUserDto createUserDto) throws UserInvalidDataException {
        if (createUserDto.getLogin() == null || createUserDto.getLogin().isEmpty()) {
            throw new UserInvalidDataException("User must have non-empty login");
        }
        if (createUserDto.getPassword() == null || createUserDto.getPassword().isEmpty()) {
            throw new UserInvalidDataException("User must have non-empty password");
        }
    }

    private void validateUpdateUser(UpdateUserDto userToUpdate) throws UserInvalidDataException {
        if (userToUpdate.getPassword() == null || userToUpdate.getPassword().isEmpty()) {
            throw new UserInvalidDataException("User must have non-empty password");
        }
    }
}
