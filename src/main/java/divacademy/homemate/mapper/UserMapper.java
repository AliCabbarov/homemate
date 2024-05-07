package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.UserRequest;
import divacademy.homemate.model.dto.response.UserResponse;
import divacademy.homemate.model.entity.Role;
import divacademy.homemate.model.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "enabled", ignore = true),
            @Mapping(target = "lastLoginIp", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "tableDetail", ignore = true),
            @Mapping(target = "userDetail", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "authorities", ignore = true),
    })
    User map(UserRequest userRequest, @MappingTarget User user);

    @Mapping(target = "role", source = "role", qualifiedByName = "roleToStringRole")
    UserResponse map(User user);

    @Named("roleToStringRole")
    default String roleToStringRole(Role role) {
        return role.getName();
    }
}
