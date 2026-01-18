package eu.qerkinaj.cohabit.user.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "addresses")
public class Address extends PanacheEntityBase {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    public UUID id;

    @Column(name = "street", nullable = false, length = 255)
    public String street;

    @Column(name = "city", nullable = false, length = 100)
    public String city;

    @Column(name = "postal_code", nullable = false, length = 20)
    public String postalCode;

    @Column(name = "country", nullable = false, length = 100)
    public String country;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

    @OneToOne
    public User user;
}