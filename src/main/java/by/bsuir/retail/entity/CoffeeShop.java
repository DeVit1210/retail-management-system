package by.bsuir.retail.entity;

import by.bsuir.retail.entity.products.Material;
import by.bsuir.retail.entity.supply.Supply;
import by.bsuir.retail.entity.users.Cashier;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "coffeeShop", fetch = FetchType.LAZY)
    private List<Cashier> cashierList = new ArrayList<>();

    @OneToMany(mappedBy = "coffeeShop", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Supply> supplyList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "warehouse")
    @MapKeyColumn
    @Column(name = "quantity")
    private Map<Material, Integer> warehouse = new HashMap<>();
}
