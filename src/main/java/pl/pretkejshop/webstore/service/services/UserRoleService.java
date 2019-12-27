package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Role;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.RoleRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.RoleDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.RoleDtoMapper;
import pl.pretkejshop.webstore.service.mapper.UserDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserDtoMapper userDtoMapper;

    public List<UserDto> getRoleUsers(Integer roleId) throws NotFoundException {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Not found role with id = " + roleId))
                .getUserList().stream()
                .map(user -> userDtoMapper.toDto(user))
                .collect(Collectors.toList());
    }

    public UserDto addRoleToUser(Integer userId, Integer roleId) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id = " + userId + "not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role with id = " + roleId + "not found"));

        role.getUserList().add(user);
        user.setRole(role);

        User userWithRole = userRepository.save(user);
        return userDtoMapper.toDto(userWithRole);
    }

    public UserDto deleteRoleFromUser(Integer userId, Integer roleId) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id = " + userId + "not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role with id = " + roleId + "not found"));

        role.getUserList().remove(user);
        user.setRole(null);

        User userWithoutRole = userRepository.save(user);
        return userDtoMapper.toDto(userWithoutRole);
    }
}
