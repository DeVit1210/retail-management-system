package by.bsuir.retail.entity.users;

import by.bsuir.retail.entity.CoffeeShop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn
@Table(name = "cashiers")
@NoArgsConstructor
@Getter
@Setter
public class Cashier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    @ManyToOne
    private CoffeeShop coffeeShop;
}
