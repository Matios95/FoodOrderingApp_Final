package pl.matek.business.dao;

import pl.matek.domain.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerDAO {
    Owner saveOwner(Owner owner);

    Optional<Owner> findByEmail(String email);

    List<Owner> findAll();
}
