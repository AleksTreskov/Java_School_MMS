package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Query("SELECT p FROM Purchase p JOIN Client c on p.client.id=c.id WHERE c.email = (:email)")
    List<Purchase> getAllByEmail(@Param("email") String email);
    @Query(nativeQuery = true,value = "SELECT sum(p.total_price) from Purchase p group by p.client_id order by sum(total_price) desc limit 10 ")
    List<Integer> findTopPrices();
}
