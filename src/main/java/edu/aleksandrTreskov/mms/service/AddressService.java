package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.dto.AddressDTO;
import edu.aleksandrTreskov.mms.entity.Address;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.mapstruct.mapper.AddressMapper;
import edu.aleksandrTreskov.mms.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;


    public List<AddressDTO> findAllAddressesByEmail(String email) {

        List<AddressDTO> addresses = new ArrayList<>();
        addressRepository.getAllByEmail(email).forEach(address -> {
            if (!address.isDeleted())
                addresses.add(AddressMapper.INSTANCE.toDTO(address));
        });
        return addresses;
    }

    /**
     * Saving address to DB
     **/

    public Address saveAddress(Address address, Client client) {
        address.setDeleted(false);
        address.setClient(client);
        addressRepository.save(address);
        return addressRepository.findByAddressInfo(client, address.getCountry(), address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat(), address.getPostcode());
    }

    /**
     * Deleting address from DB using ID
     **/
    public void deleteAddress(long id) {
        Address address;
        Optional<Address> optAddress = addressRepository.findById(id);
        if (optAddress.isPresent()) {
            address = optAddress.get();
        } else {
            throw new NoSuchElementException();
        }
        address.setDeleted(true);

        addressRepository.save(address);
    }

    public Address findById(long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent())
            return address.get();
        throw new NoSuchElementException();
    }
}
