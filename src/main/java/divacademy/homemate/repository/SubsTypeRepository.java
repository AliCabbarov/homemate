package divacademy.homemate.repository;

import divacademy.homemate.model.entity.SubsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubsTypeRepository extends JpaRepository<SubsType, Long> {
    Optional<SubsType> findByIdAndAvailable(long id, boolean available);
}