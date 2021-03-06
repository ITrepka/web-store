package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pretkejshop.webstore.model.Basket;
import pl.pretkejshop.webstore.model.PersonalData;
import pl.pretkejshop.webstore.model.Role;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.BasketRepository;
import pl.pretkejshop.webstore.repository.PersonalDataRepository;
import pl.pretkejshop.webstore.repository.RoleRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.*;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.PersonalDataDtoMapper;
import pl.pretkejshop.webstore.service.mapper.UserDtoMapper;

import javax.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDtoMapper userDtoMapper;
    @Autowired
    private PersonalDataRepository personalDataRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BasketRepository basketRepository;

    //    @PostConstruct
//    public void init() {
//        userRepository.save(new User(null, "user@pp.pl", passwordEncoder.encode("user"), null, null, null, null, null, null, null, null, roleRepository.findByName("USER").orElse(null), null));
//        userRepository.save(new User(null, "admin@pp.pl", passwordEncoder.encode("admin"), null, null, null, null, null, null, null, null, roleRepository.findByName("ADMIN").orElse(null), null));
//    }

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
    public UserDto getUserByLogin(String login) throws NotFoundException {
        return userRepository.findByEmail(login)
                .map(user -> userDtoMapper.toDto(user))
                .orElseThrow(() -> new NotFoundException("Not found user with login = " + login));
    }

    @Transactional
    public UserDto addNewUser(CreateUserDto createUserDto) throws AlreadyExistsException, InvalidDataException, NotFoundException {
        validateCreateUser(createUserDto);
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new AlreadyExistsException("User with email = " + createUserDto.getEmail() + " already exists");
        }
        PersonalData newPersonalData = personalDataRepository.save(new PersonalData());
        User user = userDtoMapper.toModel(createUserDto);
        Basket newCart = basketRepository.save(new Basket());
        user.setBasket(newCart);
        user.setPersonalData(newPersonalData);
        user.setLoyaltyPoints(0);
        user.setCreatedAt(OffsetDateTime.now());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        User savedUser = userRepository.save(user);
        RoleDto roleDto = roleService.getRoleByName("USER");
        userRoleService.addRoleToUser(savedUser.getId(), roleDto.getId());
        return userDtoMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto updateUserById(int id, UpdateUserDto userToUpdate) throws InvalidDataException, NotFoundException {
        validateUpdateUser(userToUpdate);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found User with id = " + id));
        user.setPassword(passwordEncoder.encode(userToUpdate.getPassword())); // todo szyfry
        user.setUpdatedAt(OffsetDateTime.now());
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
        if (createUserDto.getEmail() == null || createUserDto.getEmail().isEmpty()) {
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

    public UserDto getUserByEmail(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Not Found user with email " + email));
        return userDtoMapper.toDto(user);
    }
}
