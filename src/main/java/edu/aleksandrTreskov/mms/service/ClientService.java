package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.entity.Role;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.mapstruct.dto.ClientDTO;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ClientMapper;
import edu.aleksandrTreskov.mms.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

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

    public void deleteClient(long id) {
        clientRepository.findById(id).setDeleted(true);
    }

    public ClientDTO findByEmail(String email) {
        Optional<Client> client = clientRepository.findByEmail(email);
        if (client.isPresent())
        return ClientMapper.INSTANCE.toDTO(client.get());
        throw new NoSuchElementException();
    }
}
