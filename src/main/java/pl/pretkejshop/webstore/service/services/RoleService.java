package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Role;
import pl.pretkejshop.webstore.repository.RoleRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateRoleDto;
import pl.pretkejshop.webstore.service.dto.RoleDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.RoleDtoMapper;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleDtoMapper roleDtoMapper;

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> roleDtoMapper.toDto(role))
                .collect(Collectors.toList());
    }

    @Transactional
    public RoleDto getRoleById(int id) throws NotFoundException {
        return roleRepository.findById(id)
                .map(role -> roleDtoMapper.toDto(role))
                .orElseThrow(() -> new NotFoundException("Not found role with id = " + id));
    }

    @Transactional
    public RoleDto addNewRole(CreateUpdateRoleDto createRoleDto) {
        Role role = roleDtoMapper.toModel(createRoleDto);
        role.setCreatedAt(OffsetDateTime.now());
        Role savedRole = roleRepository.save(role);
        return roleDtoMapper.toDto(savedRole);
    }

    @Transactional
    public RoleDto updateRoleById(int id, CreateUpdateRoleDto roleToUpdate) throws NotFoundException {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Role with id = " + id));

        role.setName(roleToUpdate.getName());
        role.setUpdatedAt(OffsetDateTime.now());
        Role savedRole = roleRepository.save(role);
        return roleDtoMapper.toDto(savedRole);
    }

    @Transactional
    public RoleDto deleteRoleById(int id) throws NotFoundException {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found role with id = " + id));
        roleRepository.deleteById(id);
        return roleDtoMapper.toDto(role);
    }
}
