package by.bsuir.retail.entity.supply;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.RetailManagementEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime createdAt;
    private double totalCost;
}
