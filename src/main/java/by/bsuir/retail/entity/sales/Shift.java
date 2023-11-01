package by.bsuir.retail.entity.sales;

import by.bsuir.retail.entity.RetailManagementEntity;
import by.bsuir.retail.entity.users.Cashier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shifts")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class Shift implements RetailManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Cashier cashier;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean active;
}
