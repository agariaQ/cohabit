package eu.qerkinaj.cohabit.user.service;

import eu.qerkinaj.cohabit.common.Role;
import eu.qerkinaj.cohabit.user.domain.Address;
import eu.qerkinaj.cohabit.user.domain.User;
import eu.qerkinaj.cohabit.user.dto.UserDTO;
import eu.qerkinaj.cohabit.user.dto.UserInput;
import eu.qerkinaj.cohabit.user.mapper.UserMapper;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.Set;

@ApplicationScoped
public class RegistrationService {

    private static final Logger LOG = Logger.getLogger(RegistrationService.class);

    @Inject
    UserMapper userMapper;

    @Transactional
    public UserDTO register(UserInput input) {
        String email = input.email();

        LOG.infof("Starting registration process for email: %s", email);

        String roleToAssign = input.requestedRole() != null
                ? input.requestedRole().trim().toUpperCase()
                : Role.USER;

        LOG.debugf("User '%s' requested role: %s", email, roleToAssign);

        if (email == null || email.isBlank()) {
            LOG.warn("Registration rejected: Email is missing or empty.");
            throw new IllegalArgumentException("Email is required");
        }

        if (!Role.USER.equals(roleToAssign) && !Role.VENDOR.equals(roleToAssign)) {
            LOG.warnf("Registration rejected for '%s': Invalid role '%s'.", email, roleToAssign);
            throw new IllegalArgumentException("Invalid role. Allowed: USER, VENDOR");
        }

        if (User.findByEmail(email) != null) {
            LOG.warnf("Registration rejected: User '%s' already exists.", email);
            throw new IllegalArgumentException("User already exists");
        }

        User newUser = new User();
        newUser.email = email;
        newUser.password = BcryptUtil.bcryptHash(input.password());
        newUser.roles = Set.of(roleToAssign);

        newUser.persist();
        LOG.debugf("User entity persisted for '%s' with ID: %s", email, newUser.id);

        if (input.address() != null) {
            LOG.debugf("Saving address details for user '%s'...", email);

            Address address = new Address();
            address.street = input.address().street();
            address.city = input.address().city();
            address.postalCode = input.address().postalCode();
            address.country = input.address().country();

            address.user = newUser;
            newUser.address = address;

            address.persist();
        }

        LOG.infof("Registration completed successfully for user '%s' (Role: %s)", email, roleToAssign);

        return userMapper.toDto(newUser);
    }
}