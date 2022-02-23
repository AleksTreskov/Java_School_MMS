package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
