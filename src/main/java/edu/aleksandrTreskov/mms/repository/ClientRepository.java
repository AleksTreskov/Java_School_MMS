package edu.aleksandrTreskov.mms.repository;

import edu.aleksandrTreskov.mms.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
        Optional<Client> findByEmail(String username);

    Client findById(long id);
}
