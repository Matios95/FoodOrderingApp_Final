package pl.matek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.OwnerDAO;
import pl.matek.domain.Owner;
import pl.matek.infrastructure.database.entity.OwnerEntity;
import pl.matek.infrastructure.database.repository.jpa.OwnerJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.OwnerEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository implements OwnerDAO {

    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerEntityMapper ownerEntityMapper;

    @Override
    public Owner saveOwner(Owner owner) {
        OwnerEntity toSave = ownerEntityMapper.mapToEntity(owner);
        OwnerEntity saved = ownerJpaRepository.save(toSave);
        return ownerEntityMapper.mapFromEntity(saved);
    }

    @Override
    public Optional<Owner> findByEmail(String email) {
        return ownerJpaRepository.findByEmail(email)
                .map(obc -> ownerEntityMapper.mapFromEntity(obc));
    }
}
