package eu.qerkinaj.cohabit.user.resource;

import eu.qerkinaj.cohabit.user.dto.LoginRequest;
import eu.qerkinaj.cohabit.user.dto.TokenResponse;
import eu.qerkinaj.cohabit.user.dto.UserDTO;
import eu.qerkinaj.cohabit.user.dto.UserInput;
import eu.qerkinaj.cohabit.user.service.LoginService;
import eu.qerkinaj.cohabit.user.service.RegistrationService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Inject
    LoginService loginService;

    @Inject
    RegistrationService registrationService;

    @POST
    @Path("/login")
    @PermitAll
    public Response login(LoginRequest loginRequest) {
        LOG.infof("Login attempt for email: %s", loginRequest.email());

        try {
            TokenResponse token = loginService.login(loginRequest);

            LOG.infof("Login successful for user: %s", loginRequest.email());
            return Response.ok(token).build();
        }
        catch (IllegalArgumentException e) {
            LOG.warnf("Login failed for email %s: %s", loginRequest.email(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            LOG.errorf(e, "Unexpected error during login for email %s", loginRequest.email());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/register")
    @PermitAll
    public Response register(UserInput input) {
        LOG.infof("Registration attempt: Email='%s', RequestedRole='%s'", input.email(), input.requestedRole());

        try {
            UserDTO createdUser = registrationService.register(input);

            LOG.infof("User registered successfully: %s (ID: %s)", createdUser.email(), createdUser.id());
            return Response.ok(createdUser).build();
        }
        catch (IllegalArgumentException e) {
            LOG.warnf("Registration rejected for email %s: %s", input.email(), e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            LOG.errorf(e, "Registration failed unexpectedly for email %s", input.email());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}