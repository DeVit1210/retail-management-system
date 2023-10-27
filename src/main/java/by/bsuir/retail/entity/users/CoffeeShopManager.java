package by.bsuir.retail.entity.users;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.RetailManagementEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
public class CoffeeShopManager extends User implements RetailManagementEntity {
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
