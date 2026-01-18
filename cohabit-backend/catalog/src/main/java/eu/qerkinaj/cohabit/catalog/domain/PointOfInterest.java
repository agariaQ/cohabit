package eu.qerkinaj.cohabit.catalog.domain;

import eu.qerkinaj.cohabit.catalog.enums.POICategory;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "point_of_interest")
public class PointOfInterest extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    public String name;

    @Enumerated(EnumType.STRING)
    public POICategory category;

    public String subCategory;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    public Map<String, Object> properties;

    @Column(columnDefinition = "geometry(Point, 4326)")
    public Point location;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;
}
