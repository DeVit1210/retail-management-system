package by.bsuir.retail.entity.supply;

import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.products.Material;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "supply_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyLine implements RetailManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Material material;
    @ManyToOne
    private Supply supply;
    private LocalDateTime purchasedAt;
    private double purchaseCost;
    private int quantity;
}
