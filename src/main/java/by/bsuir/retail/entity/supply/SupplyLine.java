package by.bsuir.retail.entity.supply;

import by.bsuir.retail.entity.products.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "supply_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Material material;
    private LocalDateTime purchasedAt;
    private double purchaseCost;
    private int quantity;
}
