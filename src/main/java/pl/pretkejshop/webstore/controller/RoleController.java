package pl.pretkejshop.webstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    RoleService roleService;

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
