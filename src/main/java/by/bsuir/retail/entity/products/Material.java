package by.bsuir.retail.entity.products;

import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.supply.SupplyLine;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material implements RetailManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int weight;
    private double purchaseCost;
    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
    private List<SupplyLine> supplyHistory = new ArrayList<>();
}
