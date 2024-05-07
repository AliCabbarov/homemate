package divacademy.homemate.repository;

import divacademy.homemate.model.entity.BuildingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingTypeRepository extends JpaRepository<BuildingType, Long> {
}