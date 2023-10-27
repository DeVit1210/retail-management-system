package by.bsuir.retail.entity.sales;

import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.users.Cashier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements RetailManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Cashier cashier;
    private LocalDateTime createdAt;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_composition")
    @MapKeyColumn
    @Column(name = "quantity")
    private Map<Product, Integer> composition;
    private int discountPercent;
    @OneToOne
    private Payment payment;
}
