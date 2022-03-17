package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item findById(long id);

    List<Item> findTop10ByOrderByCategoryAscQuantityDesc();

    List<Item> findAllByOrderByPrice();

    @Query(nativeQuery = true, value = "SELECT distinct category from item")
    List<String> findAllCategories();

    @Query(nativeQuery = true, value = "SELECT * FROM item order by (sold*price) desc limit 10")
    List<Item> findTop10OrderBySold();

    List<Item> findAllByCategory(String category);

}
