package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.AdvertPropertyTypeRequest;
import divacademy.homemate.model.dto.response.PropertyTypeResponse;
import divacademy.homemate.model.entity.PropertyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PropertyTypeMapper {
    @Mappings({
        @Mapping(target = "id",ignore = true),
        @Mapping(target = "tableDetail",ignore = true)
    })
    PropertyType map(AdvertPropertyTypeRequest request, @MappingTarget PropertyType propertyType);
    PropertyTypeResponse map(PropertyType propertyType);

}
