package eu.qerkinaj.cohabit.user.dto;

public record AddressDTO(
        String street,
        String city,
        String postalCode,
        String country
) {
}
