package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.BuildingTypeRequest;
import divacademy.homemate.model.dto.response.BuildingTypeResponse;
import divacademy.homemate.model.entity.BuildingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BuildingTypeMapper {
    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "tableDetail",ignore = true)
    })
    BuildingType map(BuildingTypeRequest request, @MappingTarget BuildingType buildingType);
    BuildingTypeResponse map(BuildingType buildingType);
}
