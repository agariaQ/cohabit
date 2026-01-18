package eu.qerkinaj.cohabit.user.dto;

public record LoginRequest(
        String email,
        String password
) {
}
