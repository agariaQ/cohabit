package eu.qerkinaj.cohabit.user.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record TokenResponse(
        String token
) {
}
