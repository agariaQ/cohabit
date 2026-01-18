package eu.qerkinaj.cohabit.catalog.dto;

public record ApartmentFilterDTO(
        Double minSize,
        Double maxSize,
        Integer minRooms,
        String district,
        String complexName,
        String address
) {
}
