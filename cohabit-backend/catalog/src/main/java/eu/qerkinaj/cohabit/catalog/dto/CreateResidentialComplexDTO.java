package eu.qerkinaj.cohabit.catalog.dto;

public record CreateResidentialComplexDTO(
        String name,
        String street,
        String houseNumber,
        String zipCode,
        String city,
        String district,
        Double latitude,
        Double longitude
) {
}
