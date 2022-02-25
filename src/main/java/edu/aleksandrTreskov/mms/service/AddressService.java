package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.entity.Address;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.mapstruct.dto.AddressDTO;
import edu.aleksandrTreskov.mms.mapstruct.mapper.AddressMapper;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ClientMapper;
import edu.aleksandrTreskov.mms.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressDTO> getAllAddressesByEmail(String email) {

        List<AddressDTO> addresses = new ArrayList<>();
        addressRepository.getAllByEmail(email).forEach(address -> addresses.add(AddressMapper.INSTANCE.toDTO(address)));
        return addresses;
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }


    public void deleteAddress(long id) {
        addressRepository.findById(id).setDeleted(true);
    }
}
