package edu.AleksandrTreskov.mms.repository;

import edu.AleksandrTreskov.mms.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer>{
        }
