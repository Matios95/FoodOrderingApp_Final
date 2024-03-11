package pl.matek.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matek.infrastructure.database.entity.FilesEntity;

@Repository
public interface FilesJpaRepository extends JpaRepository<FilesEntity, Integer> {
}
