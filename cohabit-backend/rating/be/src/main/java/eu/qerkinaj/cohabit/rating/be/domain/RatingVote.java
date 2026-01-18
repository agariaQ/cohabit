package eu.qerkinaj.cohabit.rating.be.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rating_votes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"rating_id", "user_id"}))
public class RatingVote extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "rating_id", nullable = false)
    public UUID ratingId;

    @Column(name = "user_id", nullable = false)
    public UUID userId;

    public static boolean hasVoted(UUID ratingId, UUID userId) {
        return count("ratingId = ?1 and userId = ?2", ratingId, userId) > 0;
    }
}
