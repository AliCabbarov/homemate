package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.PropertyDetailRequest;
import divacademy.homemate.model.entity.PropertyDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PropertyDetailMapper {
    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "buildingType",ignore = true),
            @Mapping(target = "propertyTypes",ignore = true),
            @Mapping(target = "tableDetail",ignore = true),
    })
    PropertyDetail map(PropertyDetailRequest request,@MappingTarget PropertyDetail propertyDetail);
}
