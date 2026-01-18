package eu.qerkinaj.cohabit.catalog.client.user;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v1/")
@RegisterRestClient(configKey = "user-api")
public interface UserClient {

    @GET
    @Path("")
    String getUserEmail();
}
