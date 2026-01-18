package eu.qerkinaj.cohabit.rating.be.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "rating_criteria_definition")
public class RatingCriteriaDefinition extends PanacheEntityBase {

    @Id
    @Column(name = "key", length = 50)
    public String key;

    @Column(name = "label", nullable = false)
    public String label;

    @Column(name = "active", nullable = false)
    public boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;
}
