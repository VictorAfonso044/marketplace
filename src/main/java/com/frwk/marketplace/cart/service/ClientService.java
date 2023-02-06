package com.frwk.marketplace.cart.service;

import com.frwk.marketplace.cart.data.dto.ClientDTO;
import com.frwk.marketplace.cart.data.entity.ClientEntity;

public interface ClientService {
    
    ClientDTO createClient(ClientEntity client);
}
