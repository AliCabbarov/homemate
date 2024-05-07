package divacademy.homemate.repository;

import divacademy.homemate.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from _user u where (u.email = :emailOrPhone or u.phoneNumber = :emailOrPhone) and u.enabled = true")
    Optional<User> findByEmailOrPhoneNumberAndEnabled(@Param("emailOrPhone") String emailOrPhone);
    @Query("select u from _user u where u.email = :emailOrPhone or u.phoneNumber = :emailOrPhone")
    Optional<User> findByEmailOrPhoneNumber(@Param("emailOrPhone") String emailOrPhone);

}
