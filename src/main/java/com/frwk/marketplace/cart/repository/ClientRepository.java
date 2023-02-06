package com.frwk.marketplace.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.cart.data.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {

}
