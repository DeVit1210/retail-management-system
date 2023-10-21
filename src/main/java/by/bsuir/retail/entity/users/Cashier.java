package by.bsuir.retail.entity.users;

import by.bsuir.retail.entity.CoffeeShop;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Entity
@PrimaryKeyJoinColumn
@Table(name = "cashiers")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Cashier extends User {
    @Id
    private long id;
    private String fullName;
    @ManyToOne
    private CoffeeShop coffeeShop;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(Role.CASHIER.name()));
    }
}
