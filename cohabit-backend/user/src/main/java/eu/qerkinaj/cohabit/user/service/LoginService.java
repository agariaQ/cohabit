package eu.qerkinaj.cohabit.user.service;

import eu.qerkinaj.cohabit.user.domain.User;
import eu.qerkinaj.cohabit.user.dto.LoginRequest;
import eu.qerkinaj.cohabit.user.dto.TokenResponse;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@ApplicationScoped
public class LoginService {

    private static final Logger LOG = Logger.getLogger(LoginService.class);

    public TokenResponse login(LoginRequest loginRequest) {
        String email = loginRequest.email();
        LOG.infof("Processing login request for email: %s", email);

        if (email == null || email.isBlank() || loginRequest.password() == null || loginRequest.password().isBlank()) {
            LOG.warn("Login rejected: Email or password was empty.");
            throw new IllegalArgumentException("Email and password must not be empty");
        }

        User user = User.findByEmail(email);

        if (user == null) {

            LOG.warnf("Login failed: User with email '%s' not found in database.", email);
            throw new IllegalArgumentException("Invalid credentials");
        }

        if (!BcryptUtil.matches(loginRequest.password(), user.password)) {
            LOG.warnf("Login failed: Password mismatch for user '%s'.", email);
            throw new IllegalArgumentException("Invalid credentials");
        }

        LOG.debugf("User credentials valid for '%s'. Roles: %s. Generating token...", email, user.roles);

        TokenResponse token = generateToken(user);

        LOG.infof("Login successful. Token generated for user '%s' (ID: %s)", email, user.id);

        return token;
    }

    private TokenResponse generateToken(User user) {
        Set<String> groups = user.roles != null ? user.roles : Set.of();

        String tokenString = Jwt.issuer("http://localhost:8080")
                .subject(user.id.toString())
                .upn(user.email)
                .groups(groups)
                .claim("userId", user.id.toString())
                .claim("email", user.email)
                .expiresAt(Instant.now().plus(Duration.ofHours(12)))
                .sign();

        LOG.debug("JWT signed successfully.");

        return new TokenResponse(tokenString);
    }
}