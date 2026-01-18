package eu.qerkinaj.cohabit.catalog.mapper;

import eu.qerkinaj.cohabit.catalog.domain.Apartment;
import eu.qerkinaj.cohabit.catalog.domain.GeoRegion;
import eu.qerkinaj.cohabit.catalog.domain.Image;
import eu.qerkinaj.cohabit.catalog.domain.ResidentialComplex;
import eu.qerkinaj.cohabit.catalog.dto.ApartmentDTO;
import eu.qerkinaj.cohabit.catalog.dto.GeoRegionDTO;
import eu.qerkinaj.cohabit.catalog.dto.ResidentialComplexDTO;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "jakarta-cdi")
public interface CatalogMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "address", expression = "java(entity.street + \" \" + entity.houseNumber + \", \" + entity.zipCode + \" \" + entity.city)")
    @Mapping(target = "district", source = "geoRegion.name")
    @Mapping(target = "latitude", source = "location", qualifiedByName = "extractLat")
    @Mapping(target = "longitude", source = "location", qualifiedByName = "extractLon")
    @Mapping(target = "imageUrls", source = "images")
    ResidentialComplexDTO toDTO(ResidentialComplex entity);

    List<ResidentialComplexDTO> toComplexDTOs(List<ResidentialComplex> entities);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "complexId", source = "complex.id")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "imageUrls", source = "images")
    ApartmentDTO toDTO(Apartment entity);

    List<ApartmentDTO> toApartmentDTOs(List<Apartment> entities);

    @Mapping(target = "parentCode", source = "parent.code")
    GeoRegionDTO toDTO(GeoRegion entity);

    default String mapImageToString(Image image) {
        if (image == null) return null;
        return image.url;
    }

    default List<String> mapImages(List<Image> images) {
        if (images == null) return Collections.emptyList();
        return images.stream().map(this::mapImageToString).toList();
    }

    @Named("extractLat")
    default Double extractLat(Point location) {
        if (location == null) return null;
        return location.getY();
    }

    @Named("extractLon")
    default Double extractLon(Point location) {
        if (location == null) return null;
        return location.getX();
    }
}