package eu.qerkinaj.cohabit.rating.be;

import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@RegisterRestClient(configKey = "catalog-api")
public interface CatalogClient {

    @PATCH
    @Path("/api/v1/catalog/apartments/{id}/rating")
    void updateAverageRating(@PathParam("id") UUID targetId, @QueryParam("avg") double newAverage);
}
