package eu.qerkinaj.cohabit.user.dto;

public record UserInput (
        String email,
        String password,
        AddressDTO address,
        String requestedRole
){
}
