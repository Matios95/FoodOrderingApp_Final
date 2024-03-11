package pl.matek.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "placeId")
@ToString(of = {"placeId", "phone", "name"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "place")
public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Integer placeId;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity addressPlace;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    private Set<ProductEntity> productEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "placeDeliveryAddress")
    private Set<DeliveryAddressEntity> deliveryAddressEntities;
}
