package eu.qerkinaj.cohabit.catalog.dto;

import eu.qerkinaj.cohabit.catalog.enums.GeoLevel;

public record GeoRegionDTO(
        String code,
        String name,
        GeoLevel level,
        String parentCode
) {
}
