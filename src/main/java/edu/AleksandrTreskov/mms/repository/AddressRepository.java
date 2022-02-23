package edu.AleksandrTreskov.mms.repository;

import edu.AleksandrTreskov.mms.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
