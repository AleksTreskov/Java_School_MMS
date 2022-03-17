package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String username);

    Client findById(long id);

    @Query(nativeQuery = true,value =
            "SELECT *,sum(total_price) from Client c join Purchase p on c.id=p.client_id group by c.id   order by sum(total_price) desc limit 10")
    List<Client> findTop10ByPurchases();
}
