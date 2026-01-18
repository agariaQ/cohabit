package eu.qerkinaj.cohabit.catalog.client.ip;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "ip-api")
@Path("/json")
public interface IpApiClient {

    @GET
    @Path("/{ip}")
    GeoInfoDTO lookup(@PathParam("ip") String ip);

}
