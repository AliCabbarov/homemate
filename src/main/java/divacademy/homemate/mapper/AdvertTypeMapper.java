package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.AdvertTypeRequest;
import divacademy.homemate.model.dto.response.AdvertTypeResponse;
import divacademy.homemate.model.entity.AdvertType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AdvertTypeMapper {
    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "tableDetail",ignore = true)
    })
    AdvertType map(AdvertTypeRequest request, @MappingTarget AdvertType advertType);
    AdvertTypeResponse map(AdvertType advertType);

}
