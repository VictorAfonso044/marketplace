package com.frwk.marketplace.product.data.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PRODUCT")
@Getter @Setter @Builder 
@NoArgsConstructor @AllArgsConstructor
public class ProductEntity {

    @Id
    @Column(name = "ID", nullable = false, columnDefinition = "BINARY(16) NOT NULL")
    private UUID id;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;
    
    @Column(name = "PRICE", scale = 2)
    private Double price;
}
