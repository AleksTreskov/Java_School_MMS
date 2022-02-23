package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.entity.Role;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.mapstruct.dto.ClientDTO;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ClientMapper;
import edu.aleksandrTreskov.mms.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ClientDTO> getAllClients() {

        List<ClientDTO> clients = new ArrayList<>();
        clientRepository.findAll().forEach(client -> clients.add(ClientMapper.INSTANCE.toDTO(client)));
        return clients;
    }

    public void saveClient(Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent())
            throw new IllegalArgumentException("User with that email already exists");
        client.setRole(new Role(2, "ROLE_USER"));
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    public ClientDTO findById(long id) {
        Client client = clientRepository.findById(id);
        return ClientMapper.INSTANCE.toDTO(client);
    }
}
