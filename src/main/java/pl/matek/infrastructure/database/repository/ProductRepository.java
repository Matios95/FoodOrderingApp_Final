package pl.matek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.ProductDAO;

@Repository
@AllArgsConstructor
public class ProductRepository implements ProductDAO {
}