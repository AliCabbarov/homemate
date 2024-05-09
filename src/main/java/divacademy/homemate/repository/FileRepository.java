package divacademy.homemate.repository;

import divacademy.homemate.model.entity.Advert;
import divacademy.homemate.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByProduct(Advert advert);
}