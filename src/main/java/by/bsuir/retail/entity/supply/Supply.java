package by.bsuir.retail.entity.supply;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.RetailManagementEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supply implements RetailManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JsonManagedReference
    private CoffeeShop coffeeShop;
    @ManyToOne
    private Supplier supplier;
    @OneToMany(mappedBy = "supply", fetch = FetchType.EAGER)
    private List<SupplyLine> composition = new ArrayList<>();
    private LocalDateTime createdAt;
    private double totalCost;
}
