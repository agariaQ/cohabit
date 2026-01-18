package eu.qerkinaj.cohabit.catalog.client.currency.ecb;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.rest.client.reactive.jackson.runtime.serialisers.ClientJacksonMessageBodyReader;
import io.quarkus.rest.client.reactive.jackson.runtime.serialisers.ClientJacksonMessageBodyWriter;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/service/data/EXR")
@RegisterRestClient(configKey = "ecb-api")
@RegisterProvider(ClientJacksonMessageBodyReader.class)
@RegisterProvider(ClientJacksonMessageBodyWriter.class)
public interface EcbDataClient {

    @GET
    @Path("/{seriesKey}")
    @Produces("application/vnd.sdmx.data+json;version=1.0.0-wd")
    JsonNode getExchangeData(
            @PathParam("seriesKey") String seriesKey,
            @QueryParam("lastNObservations") @DefaultValue("1") int lastNObservations,
            @QueryParam("format") @DefaultValue("jsondata") String format
    );
}
