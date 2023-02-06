package com.frwk.marketplace.cart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.cart.data.dto.ClientDTO;
import com.frwk.marketplace.cart.data.entity.ClientEntity;
import com.frwk.marketplace.cart.data.parser.ClientParser;
import com.frwk.marketplace.cart.repository.ClientRepository;
import com.frwk.marketplace.cart.service.ClientService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientParser clientParser;
    
    @Override
    public ClientDTO createClient(ClientEntity client) {
        ClientEntity registered = this.clientRepository.save(client);
        return this.clientParser.mapToDTO(registered);
    }
}
