package by.bsuir.retail.entity.sales;

import by.bsuir.retail.entity.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLine {
    // TODO: contemplate about creating CreatingHistory class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Product product;
    private int quantity;
    private LocalDateTime soldAt;
    private double saleCost;
}
