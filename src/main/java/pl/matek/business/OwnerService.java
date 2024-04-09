package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.business.dao.OwnerDAO;
import pl.matek.domain.Owner;
import pl.matek.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;

    @Transactional
    public Owner findOwner(String email) {
        Optional<Owner> owner = ownerDAO.findByEmail(email);
        if (owner.isEmpty()) {
            throw new NotFoundException("Could not find owner by email: [%s]".formatted(email));
        }
        return owner.get();
    }

    public Owner ownerCreate(Owner owner) {
        return ownerDAO.saveOwner(owner);
    }

    @Transactional
    public List<Owner> findAll() {
        List<Owner> list = ownerDAO.findAll();
        log.info("Available owners: [%s]".formatted(list.size()));
        return list;
    }
}
