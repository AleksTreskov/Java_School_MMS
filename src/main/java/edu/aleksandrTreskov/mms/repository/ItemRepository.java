package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item findById(long id);

    List<Item> findTop10ByOrderByCategoryAscQuantityDesc();

    List<Item> findAllByOrderByPrice();

}
