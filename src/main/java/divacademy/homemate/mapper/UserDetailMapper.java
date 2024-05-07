package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.UserDetailRequest;
import divacademy.homemate.model.dto.response.UserDetailResponse;
import divacademy.homemate.model.entity.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserDetailMapper {
    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "tableDetail",ignore = true),
            @Mapping(target = "user",ignore = true)
    })
    UserDetail map(UserDetailRequest request, @MappingTarget UserDetail userDetail);

    UserDetailResponse map(UserDetail userDetail);
}
