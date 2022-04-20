package org.supermarket.exception;

import static java.lang.String.format;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String input) {
        super(format("Item '%s' not found", input));
    }
}
