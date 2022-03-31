package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    Optional<Purchase> findById(long id);


    @Query("SELECT p FROM Purchase p JOIN Client c on p.client.id=c.id WHERE c.email = (:email)")
    List<Purchase> getAllPurchasesByClientEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "SELECT sum(p.total_price) from Purchase p group by p.client_id order by sum(total_price) desc limit 10 ")
    List<Integer> findTopPurchasePrices();

    @Query(nativeQuery = true, value = "SELECT * from purchase order by id desc")
    List<Purchase> findAllPurchases();

    @Query(nativeQuery = true, value = "SELECT * from purchase WHERE MONTH(date_created) =(:month)")
    List<Purchase> findPurchasesInMonth(@Param("month") int month);
}
