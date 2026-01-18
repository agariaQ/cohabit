package eu.qerkinaj.cohabit.rating.be.resource;

import eu.qerkinaj.cohabit.rating.api.dto.RatingDTO;
import eu.qerkinaj.cohabit.rating.api.dto.RatingInput;
import eu.qerkinaj.cohabit.rating.be.service.RatingService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Path("/api/v1/ratings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatingResource {

    private static final Logger LOG = Logger.getLogger(RatingResource.class);

    @Inject
    RatingService ratingService;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    JsonWebToken jwt;

    @POST
    @Authenticated
    public Response create(@Valid RatingInput input) {
        String userIdRaw;
        Principal principal = securityIdentity.getPrincipal();

        if (principal instanceof JsonWebToken jwt) {
            userIdRaw = jwt.getSubject();
        } else {
            userIdRaw = principal.getName();
        }

        if (userIdRaw == null) {
            LOG.warn("Creation attempt blocked: No user ID found in security context.");
            throw new NotAuthorizedException("Keine User-ID im Token gefunden");
        }

        LOG.infof("User [%s] is submitting a rating for Target [%s]. Score: %d",
                userIdRaw, input.targetId(), input.score());

        UUID currentUserId = UUID.fromString(userIdRaw);

        RatingDTO response = ratingService.addRating(input, currentUserId);

        LOG.infof("Rating successfully created with ID: %s", response.id());

        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/target/{id}")
    @PermitAll
    public List<RatingDTO> getByTarget(@PathParam("id") UUID targetId) {
        String requestingUser = getCurrentUserLogId();

        LOG.infof("User [%s] requests rating list for Target [%s]", requestingUser, targetId);

        return ratingService.getRatingsForTarget(targetId);
    }

    @DELETE
    @Path("/{id}")
    @Authenticated
    public void delete(@PathParam("id") UUID id) {
        String userIdRaw = jwt.getSubject();
        ratingService.deleteRating(id, UUID.fromString(userIdRaw));
    }

    @POST
    @Path("/{id}/vote")
    @Authenticated
    public void voteHelpful(@PathParam("id") UUID id) {
        String userIdRaw = jwt.getSubject();
        ratingService.voteHelpful(id, UUID.fromString(userIdRaw));
    }

    @PUT
    @Path("/{id}")
    @Authenticated
    public RatingDTO update(@PathParam("id") UUID id, @Valid RatingInput input) {
        String userIdRaw = jwt.getSubject();
        return ratingService.updateRating(id, input, UUID.fromString(userIdRaw));
    }


    private String getCurrentUserLogId() {
        if (securityIdentity == null || securityIdentity.isAnonymous()) {
            return "Anonymous";
        }
        Principal principal = securityIdentity.getPrincipal();
        if (principal instanceof JsonWebToken jwt && jwt.getSubject() != null) {
            return jwt.getSubject();
        }
        return principal.getName();
    }
}