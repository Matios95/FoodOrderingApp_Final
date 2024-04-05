package pl.matek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.FoodOrderingRequestDAO;
import pl.matek.domain.Customer;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.exception.NotFoundException;
import pl.matek.infrastructure.database.entity.CustomerEntity;
import pl.matek.infrastructure.database.entity.FoodOrderingRequestEntity;
import pl.matek.infrastructure.database.repository.jpa.FoodOrderingRequesJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.matek.infrastructure.database.repository.mapper.FoodOrderingRequestEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class FoodOrderingRequestRepository implements FoodOrderingRequestDAO {

    private final FoodOrderingRequestEntityMapper foodOrderingRequestEntityMapper;
    private final FoodOrderingRequesJpaRepository foodOrderingRequesJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public List<FoodOrderingRequest> findAllWithCustomer(Customer customer) {
        CustomerEntity customerEntity = customerEntityMapper.mapToEntity(customer);
        return foodOrderingRequesJpaRepository.findByCustomer(customerEntity).stream()
                .map(foodOrderingRequestEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<FoodOrderingRequest> findById(Integer forId) {
        return foodOrderingRequesJpaRepository.findById(forId)
                .map(foodOrderingRequestEntityMapper::mapFromEntity);
    }

    @Override
    public FoodOrderingRequest save(FoodOrderingRequest foodOrderingRequest) {
        FoodOrderingRequestEntity toSave = foodOrderingRequestEntityMapper.mapToEntity(foodOrderingRequest);
        FoodOrderingRequestEntity saved = foodOrderingRequesJpaRepository.save(toSave);
        return foodOrderingRequestEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void delete(Integer forId) {
        foodOrderingRequesJpaRepository.deleteById(forId);
    }

    @Override
    public void completed(Integer forId) {
        Optional<FoodOrderingRequestEntity> toSave = foodOrderingRequesJpaRepository.findById(forId);
        if (toSave.isEmpty()) {
            throw new NotFoundException("Could not find order reques id: [%s]".formatted(forId));
        }
        toSave.get().setCompleted(true);
        foodOrderingRequesJpaRepository.save(toSave.get());
    }

}
