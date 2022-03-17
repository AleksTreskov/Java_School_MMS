package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.entity.Role;
import edu.aleksandrTreskov.mms.mapstruct.dto.ClientDTO;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ClientMapper;
import edu.aleksandrTreskov.mms.repository.ClientRepository;
import edu.aleksandrTreskov.mms.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final PurchaseRepository purchaseRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;


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

    public Map<ClientDTO, Integer> findTop10ByPurchases() {
        Map<ClientDTO, Integer> map = new LinkedHashMap<>();
        List<Client> clients = clientRepository.findTop10ByPurchases();
        List<Integer> totalPrice = purchaseRepository.findTopPurchasePrices();
        for (int i = 0; i < clients.size(); i++) {
            map.put(ClientMapper.INSTANCE.toDTO(clients.get(i)), totalPrice.get(i));
        }
        return map;


    }

    public Client findByEmail(String email) {
        Optional<Client> client = clientRepository.findByEmail(email);
        if (client.isPresent())
            return client.get();
        throw new NoSuchElementException();
    }


}