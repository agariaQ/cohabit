package eu.qerkinaj.cohabit.catalog.domain;

import eu.qerkinaj.cohabit.catalog.enums.GeoLevel;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Geometry;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "geo_region")
public class GeoRegion extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(unique = true, nullable = false)
    public String code;

    public String name;

    @Enumerated(EnumType.STRING)
    public GeoLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    public GeoRegion parent;

    @Column(columnDefinition = "geometry(Polygon, 4326)")
    public Geometry geometry;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;
}
