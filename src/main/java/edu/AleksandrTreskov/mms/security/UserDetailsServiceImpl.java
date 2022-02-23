package edu.AleksandrTreskov.mms.security;

import edu.AleksandrTreskov.mms.entity.Client;
import edu.AleksandrTreskov.mms.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Autowired
    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        return SecurityUser.fromUser(client);
    }
}
