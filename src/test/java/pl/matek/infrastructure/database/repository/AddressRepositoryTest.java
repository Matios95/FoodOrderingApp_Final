package pl.matek.infrastructure.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Address;
import pl.matek.infrastructure.database.entity.AddressEntity;
import pl.matek.infrastructure.database.repository.jpa.AddressJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.AddressEntityMapperImpl;

import static pl.matek.util.EntityFixtures.address1;
import static pl.matek.util.EntityFixtures.addressEntity1;

@ExtendWith(MockitoExtension.class)
class AddressRepositoryTest {

    @Mock
    private AddressJpaRepository addressJpaRepository;

    @Mock
    private AddressEntityMapperImpl addressEntityMapper;

    @InjectMocks
    private AddressRepository addressRepository;

    @Test
    void addressCreate() {
        //given
        Mockito.when(addressEntityMapper.mapToEntity(Mockito.any(Address.class)))
                .thenReturn(addressEntity1());
        Mockito.when(addressJpaRepository.save(Mockito.any(AddressEntity.class)))
                .thenReturn(addressEntity1());
        Mockito.when(addressEntityMapper.mapFromEntity(Mockito.any(AddressEntity.class)))
                .thenCallRealMethod();

        //when
        Address addressCreate = addressRepository.addressCreate(address1());

        //then
        Assertions.assertEquals(address1(), addressCreate);
    }
}