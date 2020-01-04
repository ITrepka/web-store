package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateRoleDto;
import pl.pretkejshop.webstore.service.dto.RoleDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public RoleDto getRoleById(@PathVariable int id) throws NotFoundException {
        return roleService.getRoleById(id);
    }

    @PostMapping
    public RoleDto addNewRole(@RequestBody CreateUpdateRoleDto createRoleDto) throws AlreadyExistsException, InvalidDataException {
        return roleService.addNewRole(createRoleDto);
    }

    @PutMapping("/{id}")
    public RoleDto updateRoleById(@PathVariable int id, @RequestBody CreateUpdateRoleDto roleToUpdate) throws NotFoundException, InvalidDataException {
        return roleService.updateRoleById(id, roleToUpdate);
    }

    @DeleteMapping("/{id}")
    public RoleDto deleteRoleById(@PathVariable int id) throws NotFoundException {
        return roleService.deleteRoleById(id);
    }
}
