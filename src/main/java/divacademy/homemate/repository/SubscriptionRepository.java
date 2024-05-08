package divacademy.homemate.repository;

import divacademy.homemate.model.entity.Subscription;
import divacademy.homemate.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("select s from Subscription s where s.user = ?1 and s.available = ?2")
    Optional<Subscription> findByUserAndAvailable(User user, boolean available);

    Optional<Subscription> findByConfirmAndAvailableAndIdAndUser(boolean confirm, boolean available, long id,User user);

    Optional<Subscription> findByUserAndAvailableAndConfirm(User user, boolean available, boolean confirm);

    List<Subscription> findByUser(User user);
}