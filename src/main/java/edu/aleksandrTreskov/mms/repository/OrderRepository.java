package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Purchase, Integer> {
}
