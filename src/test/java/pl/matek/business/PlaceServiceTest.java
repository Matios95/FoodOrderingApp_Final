package pl.matek.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.matek.domain.Address;
import pl.matek.domain.Owner;
import pl.matek.domain.Place;
import pl.matek.domain.exception.NotFoundException;
import pl.matek.infrastructure.database.repository.PlaceRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private PlaceRepository placeDAO;

    @Mock
    private OwnerService ownerService;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private PlaceService placeService;

    @Test
    void findAllPlaceWithOwner_returnListPlace() {
        //given
        var listPlaces = List.of(place1(), place2());
        when(ownerService.findOwner(anyString()))
                .thenReturn(owner1());
        when(placeDAO.findAllPlaceWithOwner(any(Owner.class)))
                .thenReturn(listPlaces);

        //when
        var result = placeService.findAllPlaceWithOwner(anyString());

        //then
        assertEquals(result.size(), 2);
    }

    @Test
    void findById_returnPlace() {
        //given
        when(placeDAO.findById(anyInt()))
                .thenReturn(Optional.ofNullable(place1()));

        //when
        var result = placeService.findById(place1().getPlaceId());

        //then
        assertNotNull(result);
        assertEquals(place1(), result);
    }

    @Test
    void findById_notFoundPlace() {
        //given
        when(placeDAO.findById(anyInt()))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NotFoundException.class, () -> placeService.findById(anyInt()));
    }

    @Test
    void findAllPlaceWithParam_returnPagePlace() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        var pagePlaces = new PageImpl<>(List.of(place1(), place2()), pageable, 10);
        when(placeDAO.findAllPlaceWithParam(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(pagePlaces);

        //when
        var result =
                placeService.findAllPlaceWithParam("99-999", "Street", 1, 10, "asc", "name");


        assertEquals(result.stream().toList().size(), 2);
    }

    @Test
    void findAll_returnListPlaces() {
        //given
        var list = List.of(place1(), place2());
        when(placeDAO.findAll()).thenReturn(list);

        //when
        var result = placeService.findAll();

        //then
        assertEquals(result.size(), 2);
    }

    @Test
    void placeCreate_returnVoidEqualsAdreessWithPlace() {
        //given
        when(addressService.createAddress(any(Address.class)))
                .thenReturn(address1());
        Place place = place1().withAddressPlace(address1());
        ArgumentCaptor<Place> captor = ArgumentCaptor.forClass(Place.class);
        ArgumentCaptor<Address> captorAddress = ArgumentCaptor.forClass(Address.class);

        //when
        placeService.placeCreate(place);

        //then
        verify(addressService).createAddress(captorAddress.capture());
        verify(placeDAO).createPlace(captor.capture());
        assertEquals(place, captor.getValue());
        assertEquals(place.getAddressPlace(), captorAddress.getValue());
    }
}