package eu.qerkinaj.cohabit.user.mapper;

import eu.qerkinaj.cohabit.user.domain.User;
import eu.qerkinaj.cohabit.user.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "jakarta-cdi",
        uses = {
                AddressMapper.class
        }
)
public interface UserMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "address", source = "address")
    UserDTO toDto(User user);
}