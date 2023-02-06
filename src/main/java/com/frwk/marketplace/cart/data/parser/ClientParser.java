package com.frwk.marketplace.cart.data.parser;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.frwk.marketplace.cart.data.dto.ClientDTO;
import com.frwk.marketplace.cart.data.entity.ClientEntity;
import com.frwk.marketplace.core.exceptions.InvalidDateException;
import com.frwk.marketplace.core.shared.DTOParser;
import com.frwk.marketplace.core.shared.utils.AppUtils;

@Component
public class ClientParser implements DTOParser<ClientDTO, ClientEntity> {

    public final static SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public ClientDTO mapToDTO(ClientEntity entity) {
        return ClientDTO.builder().cpf(entity.getIdentification())
                .nome(entity.getName())
                .dataNascimento(dtFormat.format(entity.getBirthday()))
                .email(entity.getEmail()).build();
    }

    @Override
    public ClientEntity mapFromDTO(ClientDTO dto) {
        Date date = null;
        try {
            date = dtFormat.parse(dto.getDataNascimento());
        } catch(Exception e){
            throw new InvalidDateException("dataNascimento", "Data de nascimento inválida");
        }
        return ClientEntity.builder()
                .identification(AppUtils.removeAllDotsAndHyphen(dto.getCpf()))
                .name(dto.getNome())
                .birthday(date)
                .email(dto.getEmail())
                .build();
    }
}
