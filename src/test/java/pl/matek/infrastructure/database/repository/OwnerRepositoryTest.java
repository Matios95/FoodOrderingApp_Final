package pl.matek.infrastructure.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Owner;
import pl.matek.infrastructure.database.entity.OwnerEntity;
import pl.matek.infrastructure.database.repository.jpa.OwnerJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.OwnerEntityMapperImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.owner1;
import static pl.matek.util.EntityFixtures.ownerEntity1;

@ExtendWith(MockitoExtension.class)
class OwnerRepositoryTest {

    @Mock
    private OwnerJpaRepository ownerJpaRepository;

    @Mock
    private OwnerEntityMapperImpl ownerEntityMapper;

    @InjectMocks
    private OwnerRepository ownerRepository;

    @Test
    void saveOwner() {
        //given
        when(ownerEntityMapper.mapToEntity(any(Owner.class)))
                .thenReturn(ownerEntity1());
        when(ownerJpaRepository.save(any(OwnerEntity.class)))
                .thenReturn(ownerEntity1());
        when(ownerEntityMapper.mapFromEntity(any(OwnerEntity.class)))
                .thenCallRealMethod();

        //when
        var result = ownerRepository.saveOwner(owner1());

        //then
        assertNotNull(result);
        assertEquals(owner1(), result);
    }

    @Test
    void findByEmail() {
        //given
        when(ownerJpaRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(ownerEntity1()));
        when(ownerEntityMapper.mapFromEntity(any(OwnerEntity.class)))
                .thenCallRealMethod();

        //when
        var result = ownerRepository.findByEmail(owner1().getEmail());

        //then
        assertFalse(result.isEmpty());
        assertEquals(owner1(), result.get());
    }
}