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
import java.util.stream.Collectors;

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

    public ClientDTO findById(long id) {
        Client client = clientRepository.findById(id);
        return ClientMapper.INSTANCE.toDTO(client);
    }

    public void deleteClient(long id) {
        clientRepository.findById(id).setDeleted(true);
    }

    public Map<ClientDTO, Integer> findTop10ByPurchases() {
        Map<ClientDTO, Integer> map = new LinkedHashMap<>();
        List<Client> clients = clientRepository.findTop10ByPurchases();
        List<Integer> totalPrice = purchaseRepository.findTopPrices();
        for (int i = 0; i < clients.size(); i++) {
            map.put(ClientMapper.INSTANCE.toDTO(clients.get(i)), totalPrice.get(i));
        }
        return map;


    }

    public ClientDTO findByEmail(String email) {
        Optional<Client> client = clientRepository.findByEmail(email);
        if (client.isPresent())
            return ClientMapper.INSTANCE.toDTO(client.get());
        throw new NoSuchElementException();
    }

//    public static  Map<ClientDTO, Integer> sortByValue(Map<ClientDTO, Integer> map) {
//
//        return map.entrySet()
//                .stream()
//                .sorted(Map.Entry.<ClientDTO, Integer>comparingByValue().reversed())
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (e1, e2) -> e1,
//                        LinkedHashMap::new
//                ));
//    }
//}
}