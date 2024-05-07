package divacademy.homemate.repository;

import divacademy.homemate.model.entity.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Page<Advert> findAll(Specification<Advert> specification, Pageable pageable);

    Optional<Advert> findByIdAndActive(long id, boolean active);

}