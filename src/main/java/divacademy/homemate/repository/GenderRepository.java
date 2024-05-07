package divacademy.homemate.repository;

import divacademy.homemate.model.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {
}