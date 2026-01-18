package eu.qerkinaj.cohabit.user.dto;


import java.util.Set;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        AddressDTO address,
        Set<String> roles
) {
}
