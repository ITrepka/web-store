package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUserDto;
import pl.pretkejshop.webstore.service.dto.UpdateUserDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.PersonalDataInvalidDataException;
import pl.pretkejshop.webstore.service.exception.UserAlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.UserInvalidDataException;
import pl.pretkejshop.webstore.service.exception.UserNotFoundException;
import pl.pretkejshop.webstore.service.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDto addNewUser(@RequestBody CreateUserDto createUserDto) throws UserAlreadyExistsException, UserInvalidDataException, PersonalDataInvalidDataException {
        return userService.addNewUser(createUserDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable int id, @RequestBody UpdateUserDto userToUpdate) throws UserNotFoundException, UserInvalidDataException {
        return userService.updateUserById(id, userToUpdate);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUserById(@PathVariable int id) throws UserNotFoundException {
        return userService.deleteUserById(id);
    }
}
