package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.AdvertDetailRequest;
import divacademy.homemate.model.dto.response.AdvertDetailResponse;
import divacademy.homemate.model.entity.Advert;
import divacademy.homemate.model.entity.AdvertDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AdvertDetailMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "gender", ignore = true),
    })
    AdvertDetail map(AdvertDetailRequest request, @MappingTarget AdvertDetail advertDetail);

}
