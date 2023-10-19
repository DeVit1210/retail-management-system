package by.bsuir.retail.entity;

import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.CoffeeShopManager;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "coffee_shops")
@NoArgsConstructor
@Getter
@Setter
public class CoffeeShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    @OneToOne
    private CoffeeShopManager manager;
    @OneToMany(mappedBy = "coffeeShop")
    private List<Cashier> cashierList;
}
