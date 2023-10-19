package by.bsuir.retail.entity.users;

import by.bsuir.retail.entity.CoffeeShop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Entity
@PrimaryKeyJoinColumn
@Table(name = "coffee_shop_managers")
@NoArgsConstructor
@Getter
@Setter
public class CoffeeShopManager extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    @OneToOne
    private CoffeeShop managedCoffeeShop;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(Role.COFFEE_SHOP_MANAGER.name()));
    }
}
