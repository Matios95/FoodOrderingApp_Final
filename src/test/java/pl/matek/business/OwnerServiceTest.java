package pl.matek.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Owner;
import pl.matek.domain.exception.NotFoundException;
import pl.matek.infrastructure.database.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static pl.matek.util.EntityFixtures.owner1;
import static pl.matek.util.EntityFixtures.owner2;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerDAO;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    void findOwner_returnThrow() {
        //given
        when(ownerDAO.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NotFoundException.class, () -> ownerService.findOwner(anyString()));
    }

    @Test
    void findOwner_returnOwner() {
        //given
        when(ownerDAO.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(owner1()));

        //when
        var result = ownerService.findOwner(owner1().getEmail());

        //then
        assertNotNull(result);
        assertEquals(owner1(), result);
    }

    @Test
    void ownerCreate_returnOwner() {
        //given
        when(ownerDAO.saveOwner(any(Owner.class)))
                .thenReturn(owner1());

        //when
        var result = ownerService.ownerCreate(owner1());

        //then
        assertNotNull(result);
        assertEquals(owner1(), result);
    }

    @Test
    void findAll_returnListOwner() {
        //given
        var list = List.of(owner1(), owner2());
        when(ownerDAO.findAll()).thenReturn(list);

        //when
        var result = ownerService.findAll();

        //then
        assertEquals(result.size(), 2);
    }
}