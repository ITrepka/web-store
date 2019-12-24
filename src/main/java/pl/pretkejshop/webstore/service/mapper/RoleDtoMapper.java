package pl.pretkejshop.webstore.service.mapper;

import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Role;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.service.dto.CreateUpdateRoleDto;
import pl.pretkejshop.webstore.service.dto.RoleDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleDtoMapper {
    public RoleDto toDto(Role role) {
        List<Integer> usersIds = role.getUserList() == null ? null :
                role.getUserList().stream().map(User::getId).collect(Collectors.toList());
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .userListIds(usersIds)
                .build();
    }

    public Role toModel(CreateUpdateRoleDto createRoleDto) {
        return Role.builder()
                .id(null)
                .name(createRoleDto.getName())
                .createdAt(null)
                .updatedAt(null)
                .userList(null)
                .build();
    }
}
