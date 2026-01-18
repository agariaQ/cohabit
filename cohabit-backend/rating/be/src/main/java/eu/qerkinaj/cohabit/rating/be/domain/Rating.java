package eu.qerkinaj.cohabit.rating.be.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "ratings", indexes = {
        @Index(name = "idx_rating_target", columnList = "target_id"),
        @Index(name = "idx_rating_user", columnList = "user_id")
})public class Rating extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "target_id", nullable = false)
    public UUID targetId;

    @Column(name = "user_id", nullable = false)
    public UUID userId;

    @Min(1)
    @Max(5)
    @Column(name = "total_score", nullable = false)
    public int score;

    @Column(name = "comment", nullable = false, length = 2000)
    public String comment;

    @Column(name = "helpful_votes")
    public int helpfulVotes = 0;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "thematic_ratings", columnDefinition = "jsonb")
    public Map<String, Integer> thematicRatings;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

}
