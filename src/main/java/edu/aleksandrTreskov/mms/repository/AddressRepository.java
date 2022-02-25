package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findById(long id);
    @Query("SELECT a FROM Address a JOIN Client c on a.client.id=c.id WHERE c.email = (:email)")
    List<Address> getAllByEmail(@Param("email") String email);
}
