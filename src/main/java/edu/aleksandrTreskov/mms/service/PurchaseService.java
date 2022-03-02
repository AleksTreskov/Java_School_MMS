package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.entity.Address;
import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.mapstruct.dto.PurchaseDTO;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ClientMapper;
import edu.aleksandrTreskov.mms.mapstruct.mapper.PurchaseMapper;
import edu.aleksandrTreskov.mms.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PurchaseService {
    private final ClientService clientService;
    private final PurchaseRepository purchaseRepository;


    public List<PurchaseDTO> getAllPurchases(String email) {
        List<Purchase> purchases = purchaseRepository.getAllByEmail(email);
        List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
        purchases.forEach(purchase -> purchaseDTOS.add(PurchaseMapper.INSTANCE.toDTO(purchase)));
        return purchaseDTOS;
    }

    public void save(Purchase purchase, Principal principal, Address address) {
        purchase.setClient(ClientMapper.INSTANCE.toClient(clientService.findByEmail(principal.getName())));
        purchase.setAddress(address);
        purchaseRepository.save(purchase);
    }
    public List<Integer> findTopTotalPrice(){
       return purchaseRepository.findTopPrices();
    }

}
