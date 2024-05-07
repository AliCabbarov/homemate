package divacademy.homemate.repository;

import divacademy.homemate.model.entity.User;
import divacademy.homemate.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByUser(User user);
}