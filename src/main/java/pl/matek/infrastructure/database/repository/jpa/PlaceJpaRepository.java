package pl.matek.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matek.infrastructure.database.entity.PlaceEntity;

@Repository
public interface PlaceJpaRepository extends JpaRepository<PlaceEntity, Integer> {

}
