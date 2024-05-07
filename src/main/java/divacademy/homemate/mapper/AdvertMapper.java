package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.request.AdvertRequest;
import divacademy.homemate.model.dto.response.AdvertResponse;
import divacademy.homemate.model.dto.response.AdvertsResponse;
import divacademy.homemate.model.entity.Advert;
import lombok.NoArgsConstructor;
import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface AdvertMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "active", ignore = true),
            @Mapping(target = "expiredDay", ignore = true),
            @Mapping(target = "advertTypes", ignore = true),
            @Mapping(target = "advertDetail", ignore = true),
            @Mapping(target = "propertyDetail", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "tableDetail", ignore = true),
    })
    Advert map(AdvertRequest request, @MappingTarget Advert advert);

    AdvertResponse map(Advert advert);

    @Mapping(target = "expiredDay", source = "expiredDay", qualifiedByName = "calculateExpiredDate")
    AdvertsResponse mapToAdverts(Advert advert);

    @Named("calculateExpiredDate")
    default LocalDate calculateExpiredDay(long expiredDay){
        return LocalDate.now().plusDays(expiredDay);
    }
}
