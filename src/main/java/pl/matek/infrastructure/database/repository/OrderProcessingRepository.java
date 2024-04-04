package pl.matek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.OrderProcessingDAO;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Order;
import pl.matek.domain.Product;
import pl.matek.infrastructure.database.entity.FoodOrderingRequestEntity;
import pl.matek.infrastructure.database.entity.OrderEntity;
import pl.matek.infrastructure.database.entity.ProductEntity;
import pl.matek.infrastructure.database.repository.jpa.OrderJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.FoodOrderingRequestEntityMapper;
import pl.matek.infrastructure.database.repository.mapper.OrderEntityMapper;
import pl.matek.infrastructure.database.repository.mapper.ProductEntityMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrderProcessingRepository implements OrderProcessingDAO {

    private final OrderEntityMapper orderEntityMapper;
    private final OrderJpaRepository orderJpaRepository;
    private final FoodOrderingRequestEntityMapper foodOrderingRequestEntityMapper;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public List<Order> findByFoodOrderingRequest(FoodOrderingRequest foodOrderingRequest) {
        FoodOrderingRequestEntity foodOrderingRequestEntity = foodOrderingRequestEntityMapper
                .mapToEntity(foodOrderingRequest);
        return orderJpaRepository.findAllByFoodOrderingRequestEntity(foodOrderingRequestEntity).stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void create(Order order) {
        OrderEntity toSave = orderEntityMapper.mapToEntity(order);
        orderJpaRepository.save(toSave);
    }

    @Override
    public List<Order> findAllOrderWithProduct(Product product) {
        ProductEntity productEntity = productEntityMapper.mapToEntity(product);
        return orderJpaRepository.findAllByProduct(productEntity).stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }
}
