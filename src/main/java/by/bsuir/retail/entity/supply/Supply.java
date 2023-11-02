package by.bsuir.retail.entity.supply;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.products.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "supply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supply implements RetailManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private CoffeeShop coffeeShop;
    @ManyToOne
    private Supplier supplier;
    @OneToMany(mappedBy = "supply", fetch = FetchType.EAGER)
    private List<SupplyLine> composition;
    private double totalCost;
}
