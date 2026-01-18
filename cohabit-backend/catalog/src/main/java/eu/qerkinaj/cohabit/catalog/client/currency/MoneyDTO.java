package eu.qerkinaj.cohabit.catalog.client.currency;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MoneyDTO(
        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
        BigDecimal amount,

//        @NotBlank(message = "Currency is required")
//        @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter ISO code")
        SupportedCurrency currency
) {
}
