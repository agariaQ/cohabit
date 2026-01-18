package eu.qerkinaj.cohabit.catalog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "image")
public class Image extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(nullable = false)
    public String url;

    public String caption;

    public boolean isPrimary;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    @JsonIgnore
    public Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "complex_id")
    @JsonIgnore
    public ResidentialComplex complex;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    public Instant createdAt;
}