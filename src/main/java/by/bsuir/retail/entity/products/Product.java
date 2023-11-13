package by.bsuir.retail.entity.products;

import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.sales.OrderLine;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product implements RetailManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int weight;
    private double saleCost;
    @OneToOne(mappedBy = "createdProduct")
    private TechProcess techProcess;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderLine> salesHistory;
}
