package pl.matek.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matek.infrastructure.database.entity.CustomerEntity;
import pl.matek.infrastructure.database.entity.FoodOrderingRequestEntity;

import java.util.List;

@Repository
public interface FoodOrderingRequesJpaRepository extends JpaRepository<FoodOrderingRequestEntity, Integer> {
    List<FoodOrderingRequestEntity> findByCustomer(CustomerEntity customerEntity);

    List<FoodOrderingRequestEntity> findAllByCompleted(boolean completed);
}
