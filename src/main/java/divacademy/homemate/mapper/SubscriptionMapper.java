package divacademy.homemate.mapper;

import divacademy.homemate.model.dto.response.SubsResponse;
import divacademy.homemate.model.entity.SubsType;
import divacademy.homemate.model.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    @Mappings({
            @Mapping(target = "type",source = "subsType",qualifiedByName = "typeFromSubsType")
    })
    SubsResponse map(Subscription subscription);
    @Named("typeFromSubsType")
    default String typeFromSubsType(SubsType subsType){
        return subsType.getName();
    }
}
