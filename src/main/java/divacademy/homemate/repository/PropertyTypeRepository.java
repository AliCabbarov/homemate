package divacademy.homemate.repository;

import divacademy.homemate.model.entity.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyTypeRepository extends JpaRepository<PropertyType, Long> {
    List<PropertyType> findByAvailable(boolean available);
}