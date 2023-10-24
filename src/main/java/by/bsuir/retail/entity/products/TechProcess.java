package by.bsuir.retail.entity.products;

import by.bsuir.retail.entity.products.Product;
import by.bsuir.retail.entity.products.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Table(name = "tech_processes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Product createdProduct;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "material_composition")
    @MapKeyColumn
    @Column(name = "quantity")
    private Map<Material, Integer> ingredients;
}
