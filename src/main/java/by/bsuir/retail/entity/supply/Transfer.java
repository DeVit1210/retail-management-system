package by.bsuir.retail.entity.supply;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.products.Material;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Transfer implements RetailManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime createdAt;
    @ManyToOne
    private CoffeeShop fromCoffeeShop;
    @ManyToOne
    private CoffeeShop toCoffeeShop;
    @ManyToOne
    private Material material;
    private int quantity;
}
