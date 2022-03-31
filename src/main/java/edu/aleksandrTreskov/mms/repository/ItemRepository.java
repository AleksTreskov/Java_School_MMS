package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query("SELECT i FROM Item i WHERE i.isDeleted = false")
    List<Item> findAllByDeleted();

    Item findById(long id);

    List<Item> findTop10ByOrderByCategoryAscQuantityDesc();

    @Query("SELECT distinct i.category from Item i order by i.category")
    List<String> findAllCategories();

    @Query(nativeQuery = true, value = "SELECT * FROM item order by (sold*price) desc limit 10")
    List<Item> findTop10OrderBySold();

    @Query("SELECT i FROM Item i WHERE i.isDeleted = false and i.category = (:category)")
    Page<Item> findAllByCategory(@Param("category") String category, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * from item where name like Concat('%',(:searchText),'%') " +
            "or category like Concat('%',(:searchText),'%')or brand like Concat('%',(:searchText),'%') or model like Concat('%',(:searchText),'%')")
    List<Item> searchByText(@Param("searchText") String searchText);
}
