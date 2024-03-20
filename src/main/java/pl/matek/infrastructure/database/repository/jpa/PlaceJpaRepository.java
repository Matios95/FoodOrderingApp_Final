package pl.matek.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.OpaqueTokenDsl;
import org.springframework.stereotype.Repository;
import pl.matek.infrastructure.database.entity.OwnerEntity;
import pl.matek.infrastructure.database.entity.PlaceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceJpaRepository extends JpaRepository<PlaceEntity, Integer> {

    List<PlaceEntity> findAllByOwner(OwnerEntity ownerEntity);

    Optional<PlaceEntity> findByPlaceId(Integer placeId);
}
