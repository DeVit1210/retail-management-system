package by.bsuir.retail.entity.products;

import by.bsuir.retail.entity.sales.OrderLine;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int weight;
    private double saleCost;
    @OneToMany(mappedBy = "product")
    private List<OrderLine> salesHistory;
}
