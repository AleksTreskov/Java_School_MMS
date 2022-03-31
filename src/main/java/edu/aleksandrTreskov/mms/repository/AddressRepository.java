package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Address;
import edu.aleksandrTreskov.mms.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findById(long id);

    @Query("SELECT a FROM Address a JOIN Client c on a.client.id=c.id WHERE c.email = (:email)")
    List<Address> getAllByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "SELECT * FROM address a where client_id =(:client) and country=(:country) " +
            "and city = (:city) and street = (:street)" +
            " and building = (:building)and flat = (:flat) and postcode=(:postcode) and is_deleted is false")
    Address findByAddressInfo(@Param("client") Client client, @Param("country") String country,
                              @Param("city") String city, @Param("street") String street,
                              @Param("building") String building, @Param("flat") int flat, @Param("postcode") String postcode);
}
