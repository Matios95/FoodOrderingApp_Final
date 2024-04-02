package pl.matek.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matek.infrastructure.database.entity.FoodOrderingRequestEntity;
import pl.matek.infrastructure.database.entity.OrderEntity;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findAllByFoodOrderingRequestEntity(FoodOrderingRequestEntity foodOrderingRequestEntity);
}
