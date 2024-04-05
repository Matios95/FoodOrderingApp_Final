package pl.matek.infrastructure.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food_ordering_app_user")
public class UserEntity {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "food_ordering_app_user_role",
            joinColumns = @JoinColumn(name = "email"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;
}
