package eu.qerkinaj.cohabit.user.mapper;

import eu.qerkinaj.cohabit.user.domain.Address;
import eu.qerkinaj.cohabit.user.dto.AddressDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi")
public interface AddressMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "country", source = "country")
    AddressDTO toDto(Address address);

}
