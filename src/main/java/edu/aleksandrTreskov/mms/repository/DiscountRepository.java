package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountCode,Long> {
    Optional<DiscountCode> findByName(String name);
    @Query(nativeQuery = true,value ="SELECT * FROM discount_code where is_deleted is false" )
    List<DiscountCode> findAll();
}
