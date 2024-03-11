package pl.matek.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.CustomerDAO;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {
}
