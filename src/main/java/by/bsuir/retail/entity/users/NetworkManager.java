package by.bsuir.retail.entity.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn
@Table(name = "network_managers")
@NoArgsConstructor
@Getter
@Setter
public class NetworkManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
}
