package by.bsuir.retail.entity.products;

import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.supply.SupplyLine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materials")
@Getter
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
