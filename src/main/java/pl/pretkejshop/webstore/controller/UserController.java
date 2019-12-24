package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUserDto;
import pl.pretkejshop.webstore.service.dto.UpdateUserDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) throws NotFoundException {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDto addNewUser(@RequestBody CreateUserDto createUserDto) throws AlreadyExistsException, InvalidDataException {
        return userService.addNewUser(createUserDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUserById(@PathVariable int id, @RequestBody UpdateUserDto userToUpdate) throws NotFoundException, InvalidDataException {
        return userService.updateUserById(id, userToUpdate);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUserById(@PathVariable int id) throws NotFoundException {
        return userService.deleteUserById(id);
    }
}
