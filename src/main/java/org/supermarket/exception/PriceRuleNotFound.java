package org.supermarket.exception;

import static java.lang.String.format;

public class PriceRuleNotFound extends RuntimeException {

    public PriceRuleNotFound(String message) {
        super(format("Price rule not found by '%s'", message));
    }
}
