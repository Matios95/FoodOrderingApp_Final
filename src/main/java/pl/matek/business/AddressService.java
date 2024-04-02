package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.business.dao.AddressDAO;
import pl.matek.domain.Address;

@Slf4j
@Service
@AllArgsConstructor
public class AddressService {

    private final AddressDAO addressDAO;

    public Address createAddress(Address address) {
          return addressDAO.addressCreate(address);
    }
}
