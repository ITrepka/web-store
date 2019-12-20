package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUserDto;
import pl.pretkejshop.webstore.service.dto.UpdateUserDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.UserNotFoundException;
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

    @PostConstruct
    public void init() {

    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userDtoMapper.toDto(user))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(int id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(user -> userDtoMapper.toDto(user))
                .orElseThrow(() -> new UserNotFoundException("Not found user with id = " + id));
    }

    public UserDto addNewUser(CreateUserDto createUserDto) {
        validateCreateUser(createUserDto);
        User user = userDtoMapper.toModel(createUserDto);
        user.setLoyaltyPoints(0);
        user.setCreatedAt(OffsetDateTime.now());
        //password set na zaszyfrowane todo
        return userDtoMapper.toDto(user);
    }

    public UserDto updateUserById(int id, UpdateUserDto userToUpdate) {
    }

    public UserDto deleteUserById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Not found user with id = " + id));
        userRepository.delete(user);
        return userDtoMapper.toDto(user);
    }
}
