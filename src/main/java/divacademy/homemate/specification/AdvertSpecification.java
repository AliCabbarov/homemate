package divacademy.homemate.specification;

import divacademy.homemate.model.entity.Advert;
import divacademy.homemate.model.entity.AdvertDetail;
import divacademy.homemate.model.entity.Gender;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.metamodel.Bindable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AdvertSpecification {
    public Specification<Advert> hasFirstPrice(Double firstPrice) {
        return ((root, query, criteriaBuilder) -> firstPrice != null ? criteriaBuilder.greaterThan(root.get("amount"), firstPrice) : criteriaBuilder.conjunction());
    }

    public Specification<Advert> isActive() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"), true));
    }

    public Specification<Advert> hasSecondPrice(Double secondPrice) {
        return ((root, query, criteriaBuilder) -> secondPrice != null ? criteriaBuilder.lessThan(root.get("amount"), secondPrice) : criteriaBuilder.conjunction());
    }

    public Specification<Advert> hasGenderId(Long genderId) {
        return ((root, query, criteriaBuilder) -> genderId != null ? criteriaBuilder.equal(root.join("advertDetail").join("gender").get("id"), genderId) : criteriaBuilder.conjunction());

    }

    public Specification<Advert> hasBuildingTypeId(Long buildingTypeId) {
        return ((root, query, criteriaBuilder) -> buildingTypeId != null ? criteriaBuilder.equal(root.join("propertyDetail").join("buildingType").get("id"), buildingTypeId) : criteriaBuilder.conjunction());
    }

    public Specification<Advert> hasPropertyTypeId(Long propertyTypeId) {
        return ((root, query, criteriaBuilder) -> propertyTypeId != null ? criteriaBuilder.equal(root.join("propertyDetail").join("propertyTypes").get("id"), propertyTypeId) : criteriaBuilder.conjunction());
    }

    public Specification<Advert> none() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
}
