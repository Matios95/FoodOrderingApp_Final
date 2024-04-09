package pl.matek.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Place;
import pl.matek.infrastructure.database.entity.PlaceEntity;
import pl.matek.infrastructure.database.entity.ProductEntity;
import pl.matek.infrastructure.database.repository.jpa.ProductJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.PlaceEntityMapperImpl;
import pl.matek.infrastructure.database.repository.mapper.ProductEntityMapperImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @Mock
    private ProductJpaRepository productJpaRepository;

    @Mock
    private ProductEntityMapperImpl productEntityMapper;

    @Mock
    private PlaceEntityMapperImpl placeEntityMapper;

    @InjectMocks
    private ProductRepository productRepository;

    @Test
    void findAllProductWithPlace() {
        //given
        var listEntity = List.of(productEntity1(), productEntity1());
        var list = List.of(product1(), product1());
        when(placeEntityMapper.mapToEntity(any(Place.class)))
                .thenReturn(placeEntity1());
        when(productJpaRepository.findAllByPlace(any(PlaceEntity.class)))
                .thenReturn(listEntity);
        when(productEntityMapper.mapFromEntity(any(ProductEntity.class)))
                .thenCallRealMethod();

        //when
        var result = productRepository.findAllProductWithPlace(place1());

        //then
        assertEquals(result.size(), 2);
        assertEquals(list, result);
    }

    @Test
    void findByCode() {
        //given
        when(productJpaRepository.findByProductCode(anyString()))
                .thenReturn(productEntity1());
        when(productEntityMapper.mapFromEntity(productEntity1()))
                .thenCallRealMethod();

        //when
        var result = productRepository.findByCode(anyString());

        //then
        assertNotNull(result);
        assertEquals(product1(), result);
    }
}