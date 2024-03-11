package pl.matek.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "addressId")
@ToString(of = {"addressId", "country", "postcode", "street", "streetNumber"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "country")
    private String country;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "street")
    private String street;

    @Column(name = "street_number")
    private Integer streetNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "addressPlace")
    private Set<PlaceEntity> placeEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "addressCustomer")
    private Set<CustomerEntity> customerEntities;
}
