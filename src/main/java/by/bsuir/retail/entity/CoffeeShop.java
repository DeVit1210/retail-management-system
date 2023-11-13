package by.bsuir.retail.entity;

import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.users.Cashier;
import by.bsuir.retail.entity.users.CoffeeShopManager;
import by.bsuir.retail.entity.products.Material;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "coffee_shops")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CoffeeShop {

    // TODO: связать Shift c CoffeeShop, a Cashier с Shift
    // Как Cashier будет знать, к какой кофейне он относится?
    // Возможно, он будет выбирать, в какой кофейне открыть смену перед тем, как это сделать

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;

    @OneToOne
    private CoffeeShopManager manager;

    @OneToMany(mappedBy = "coffeeShop")
    private List<Cashier> cashierList;

    @OneToMany(mappedBy = "coffeeShop")
    private List<Supply> supplyList;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "warehouse")
    @MapKeyColumn
    @Column(name = "quantity")
    private Map<Material, Integer> warehouse;
}
