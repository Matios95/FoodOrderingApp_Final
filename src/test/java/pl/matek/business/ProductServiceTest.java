package pl.matek.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Place;
import pl.matek.domain.Product;
import pl.matek.infrastructure.database.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.place1;
import static pl.matek.util.EntityFixtures.product1;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productDAO;

    @InjectMocks
    private ProductService productService;

    @Test
    void findAllProductWithPlace_returnListProduct() {
        //given
        var listPlaces = List.of(product1(), product1().withProductCode("8540905475"));
        when(productDAO.findAllProductWithPlace(any(Place.class)))
                .thenReturn(listPlaces);

        //when
        var result = productService.findAllProductWithPlace(place1());

        //then
        assertEquals(result.size(), 2);
    }

    @Test
    void findByCode_returnProduct() {
        //given
        when(productDAO.findByCode(anyString()))
                .thenReturn(product1());

        //when
        var reuslt = productService.findByCode(anyString());

        //then
        assertNotNull(reuslt);
    }

    @Test
    void productCode_returnVoid() {
        //given
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        //when
        productService.productCreate(product1());

        //then
        verify(productDAO).productCreate(captor.capture());
        assertEquals(product1(), captor.getValue());
    }
}