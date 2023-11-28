package by.bsuir.retail.entity.sales;

import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.users.Cashier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private int discountPercent;
    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<OrderLine> composition = new ArrayList<>();
    @OneToOne(mappedBy = "order")
    private Payment payment;
}
