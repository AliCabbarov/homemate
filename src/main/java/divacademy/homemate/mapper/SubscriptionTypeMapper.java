package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.SubscriptionTypeRequest;
import divacademy.homemate.model.dto.response.SubsTypeResponse;
import divacademy.homemate.model.entity.SubsType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SubscriptionTypeMapper {
    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "tableDetail",ignore = true)
    })
    SubsType map(SubscriptionTypeRequest request, @MappingTarget SubsType subsType);
    SubsTypeResponse map(SubsType subsType);
}
