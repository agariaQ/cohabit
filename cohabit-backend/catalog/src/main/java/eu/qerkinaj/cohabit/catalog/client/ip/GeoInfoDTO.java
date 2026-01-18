package eu.qerkinaj.cohabit.catalog.client.ip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoInfoDTO {
    public String status;
    public String country;
    public String countryCode;
    public String regionName;
    public String city;
    public double lat;
    public double lon;
    public String query;
}
