package by.bsuir.retail.repository.supply;

import by.bsuir.retail.entity.CoffeeShop;
import by.bsuir.retail.entity.supply.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long>, JpaSpecificationExecutor<Transfer> {
    List<Transfer> findAllByFromCoffeeShop(CoffeeShop coffeeShop);
    List<Transfer> findAllByToCoffeeShop(CoffeeShop coffeeShop);
    @Query("select t from Transfer t where t.fromCoffeeShop = :from and t.toCoffeeShop = :to")
    List<Transfer> findAllBetweenCoffeeShops(CoffeeShop from, CoffeeShop to);
}
