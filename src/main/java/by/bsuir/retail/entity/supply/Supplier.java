package by.bsuir.retail.entity.supply;

import by.bsuir.retail.entity.RetailManagementEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements RetailManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String companyName;
    private String phoneNumber;
    private String address;
    private String email;

    @OneToMany(mappedBy = "supplier")
    private List<Supply> supplyList = new ArrayList<>();

}
