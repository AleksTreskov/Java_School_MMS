package edu.AleksandrTreskov.mms.repository;

import edu.AleksandrTreskov.mms.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
