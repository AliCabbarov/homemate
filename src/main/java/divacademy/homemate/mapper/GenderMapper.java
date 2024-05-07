package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.GenderRequest;
import divacademy.homemate.model.dto.response.GenderResponse;
import divacademy.homemate.model.entity.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GenderMapper {
    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "tableDetail",ignore = true)
    })
    Gender map(GenderRequest request, @MappingTarget Gender gender);
    GenderResponse map(Gender gender);
}
