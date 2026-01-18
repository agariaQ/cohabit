package eu.qerkinaj.cohabit.catalog.client.currency;

import java.util.Currency;

public enum SupportedCurrency {

    USD("United States Dollar", "USD"),
    EUR("Euro", "EUR"),
    GBP("British Pound Sterling", "GBP"),
    JPY("Japanese Yen", "JPY");

    private final String displayName;

    private final Currency delegate;

    SupportedCurrency(String displayName, String isoCode) {
        this.displayName = displayName;
        this.delegate = Currency.getInstance(isoCode);
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDefaultFractionDigits() {
        return delegate.getDefaultFractionDigits();
    }

    public String getSymbol() {
        return delegate.getSymbol();
    }

    public String getIsoCode() {
        return delegate.getCurrencyCode();
    }

    @Override
    public String toString() {
        return delegate.getCurrencyCode();
    }
}
