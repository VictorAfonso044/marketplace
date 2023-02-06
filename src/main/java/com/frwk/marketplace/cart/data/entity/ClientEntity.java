package com.frwk.marketplace.cart.data.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CLIENT")
@Getter @Setter @Builder 
@NoArgsConstructor @AllArgsConstructor
public class ClientEntity {
    
    @Id
    @Column(name = "IDENTIFICATION", length = 14, nullable = false)
    private String identification;

    @Column(name = "NAME", length = 200, nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY", nullable = false)
    private Date birthday;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<CartEntity> cart;
}

