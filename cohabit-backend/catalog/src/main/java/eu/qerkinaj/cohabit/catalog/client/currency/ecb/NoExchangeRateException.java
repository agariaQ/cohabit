package eu.qerkinaj.cohabit.catalog.client.currency.ecb;

import eu.qerkinaj.cohabit.catalog.client.currency.SupportedCurrency;

import java.io.Serial;

public class NoExchangeRateException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NoExchangeRateException(String message) {
        super(message);
    }

    /**
     * Constructs an exception with a standard message
     * based on the two currencies.
     */
    public NoExchangeRateException(SupportedCurrency from, SupportedCurrency to) {
        super(String.format("No exchange rate available for %s → %s",
                from.getIsoCode().toUpperCase(),
                to.getIsoCode().toUpperCase()));
    }

    /**
     * If you ever want to wrap another exception (e.g. JSON parse failure),
     * this constructor lets you preserve the cause.
     */
    public NoExchangeRateException(String message, Throwable cause) {
        super(message, cause);
    }
}