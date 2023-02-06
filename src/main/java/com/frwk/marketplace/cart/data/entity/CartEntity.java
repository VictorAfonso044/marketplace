package com.frwk.marketplace.cart.data.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CART")
@Getter @Setter @Builder 
@NoArgsConstructor @AllArgsConstructor
public class CartEntity {
    
    @Id
    @Column(name = "ID", nullable = false, columnDefinition = "BINARY(16) NOT NULL")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private ClientEntity client;

    @Column(name = "CLOSED", nullable = false)
    private boolean isClosed;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<CartItemEntity> itens;
}
