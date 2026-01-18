package eu.qerkinaj.cohabit.rating.api.dto;

import java.util.Map;
import java.util.UUID;

public record RatingInput(
        UUID targetId,
        int score,
        String comment,
        Map<String, Integer> thematicRatings
) {
}
