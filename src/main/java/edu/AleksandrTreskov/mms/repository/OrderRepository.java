package edu.AleksandrTreskov.mms.repository;

import edu.AleksandrTreskov.mms.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Purchase, Integer> {
}
