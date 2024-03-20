package pl.matek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.AddressDAO;
import pl.matek.domain.Address;
import pl.matek.infrastructure.database.entity.AddressEntity;
import pl.matek.infrastructure.database.repository.jpa.AddressJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.AddressEntityMapper;

@Repository
@AllArgsConstructor
public class AddressRepository implements AddressDAO {

    private final AddressJpaRepository addressJpaRepository;
    private final AddressEntityMapper addressEntityMapper;

    @Override
    public Address addressCreate(Address address) {
        AddressEntity toSave = addressEntityMapper.mapToEntity(address);
        AddressEntity saved = addressJpaRepository.save(toSave);
        return addressEntityMapper.mapFromEntity(saved);
    }
}
