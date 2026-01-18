package eu.qerkinaj.cohabit.catalog.client.currency.ecb;

import com.fasterxml.jackson.databind.JsonNode;
import eu.qerkinaj.cohabit.catalog.client.currency.MoneyDTO;
import eu.qerkinaj.cohabit.catalog.client.currency.SupportedCurrency;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.util.Iterator;

@ApplicationScoped
public class EcbDataService {

    private static final Logger LOG = Logger.getLogger(EcbDataService.class);

    private static final String FREQ = "D";
    private static final String EXR_TYPE = "SP00";
    private static final String EXR_SUFFIX = "A";
    private static final String FORMAT = "jsondata";

    @Inject
    @RestClient
    EcbDataClient ecbDataClient;

    @ConfigProperty(name = "feature.ecb.client")
    boolean enable;

    public MoneyDTO convertCurrency(MoneyDTO moneyDTO, SupportedCurrency currency) {
        if (!moneyDTO.currency().equals(currency)) {
            double rate = getLatestRate(moneyDTO.currency(), currency);
            LOG.debugf("Got rate %f for target currency %s", rate, currency.getIsoCode());
            return new MoneyDTO(moneyDTO.amount().multiply(BigDecimal.valueOf(rate)), currency);
        }
        return moneyDTO;
    }

    @CacheResult(cacheName = "ecb-currency-rate")
    public double getLatestRate(SupportedCurrency from, SupportedCurrency to) {
        String seriesKey = buildSeriesKey(from, to);

        LOG.debugf("Built seriesKey: %s", seriesKey);
        LOG.debugf("ECB Client enabled: %b", enable);

        if (enable) {
            LOG.infof("Getting currency rate from %s to %s", from, to);

            try {
                JsonNode root = ecbDataClient.getExchangeData(seriesKey, 1, FORMAT);
                JsonNode seriesNode = root.path("dataSets").get(0).path("series");
                Iterator<String> it = seriesNode.fieldNames();

                if (!it.hasNext()) {
                    LOG.warnf("No data found for series %s", seriesKey);
                    throw new NoExchangeRateException(from, to);
                }

                String dynKey = it.next();
                JsonNode observationsNode = seriesNode.path(dynKey).path("observations").path("0");
                JsonNode obsArray = observationsNode.get(0);

                LOG.debugf("ObsArray content: %s", obsArray);

                if (obsArray == null) {
                    LOG.warnf("No observation data found for dynamic key %s", dynKey);
                    throw new NoExchangeRateException(
                            String.format("Empty observations for %s → %s on series %s",
                                    from, to, dynKey));
                }
                return obsArray.asDouble();

            } catch (Exception e) {
                LOG.errorf(e, "Failed to fetch exchange rate for %s", seriesKey);
                throw e;
            }
        }

        LOG.info("ECB Client is deactivated via configuration");
        return 0.0;
    }

    //https://data-api.ecb.europa.eu/service/data/EXR/D.USD.EUR.SP00.A?lastNObservations=1&format=jsondata
    private String buildSeriesKey(SupportedCurrency from, SupportedCurrency to) {
        return String.join(".",
                FREQ,
                from.getIsoCode().toUpperCase(),
                to.getIsoCode().toUpperCase(),
                EXR_TYPE,
                EXR_SUFFIX);
    }
}