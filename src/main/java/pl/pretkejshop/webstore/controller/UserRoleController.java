package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.UserRoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/role/{roleId}/users")
    public List<UserDto> getUsersWithRole(@PathVariable Integer roleId) throws NotFoundException {
        return userRoleService.getRoleUsers(roleId);
    }

    @PostMapping("/user/{userId}/role/{roleId}")
    private UserDto addRoleToUser(@PathVariable Integer userId, @PathVariable Integer roleId) throws NotFoundException {
        return userRoleService.addRoleToUser(userId, roleId);
    }

    @DeleteMapping("/user/{userId}/role/{roleId}")
    private UserDto deleteRoleFromUser(@PathVariable Integer userId, @PathVariable Integer roleId) throws NotFoundException {
        return userRoleService.deleteRoleFromUser(userId, roleId);
    }
}
