package edu.aleksandrTreskov.mms.mapstruct.mapper;

import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.dto.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO toDTO(Client client);

    Client toClient(ClientDTO clientDTO);
}
