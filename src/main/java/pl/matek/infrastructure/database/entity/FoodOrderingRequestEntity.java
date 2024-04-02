package pl.matek.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "foodOrderingRequestId")
@ToString(of = {"foodOrderingRequestId", "foodOrderingRequestCode", "datetime"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_ordering_request")
public class FoodOrderingRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_ordering_request_id")
    private Integer foodOrderingRequestId;

    @Column(name = "food_ordering_request_code", unique = true)
    private String foodOrderingRequestCode;

    @Column(name = "datetime")
    private OffsetDateTime datetime;

    @Column(name = "completed")
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "foodOrderingRequestEntity", orphanRemoval = true)
    private Set<OrderEntity> orderEntities;

}
