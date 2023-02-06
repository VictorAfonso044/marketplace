package com.frwk.marketplace.core.shared;

public interface DTOParser <R, E> {
    
    public  R mapToDTO(final E entity);
    
    public E mapFromDTO(final R dto);
}

