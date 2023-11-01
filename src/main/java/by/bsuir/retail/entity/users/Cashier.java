package by.bsuir.retail.entity.users;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.sales.Shift;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn
@Table(name = "cashiers")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Cashier extends User implements RetailManagementEntity {
    @Id
    private long id;
    private String fullName;
    @ManyToOne
    private CoffeeShop coffeeShop;
    @OneToMany(mappedBy = "cashier", fetch = FetchType.LAZY)
    private List<Shift> shiftList;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(Role.CASHIER.name()));
    }
}
