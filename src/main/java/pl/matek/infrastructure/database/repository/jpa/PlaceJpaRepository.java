package pl.matek.infrastructure.database.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.matek.infrastructure.database.entity.OwnerEntity;
import pl.matek.infrastructure.database.entity.PlaceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceJpaRepository extends JpaRepository<PlaceEntity, Integer> {

    List<PlaceEntity> findAllByOwner(OwnerEntity ownerEntity);

    Optional<PlaceEntity> findByPlaceId(Integer placeId);

    @Query("""
            SELECT prd FROM PlaceEntity prd
            WHERE prd.addressPlace.postcode = :postcode
            AND prd.addressPlace.street = :address
            """)
    Page<PlaceEntity> findAllParams(final @Param("postcode") String postcode, final @Param("address") String street, Pageable pageable);
}
