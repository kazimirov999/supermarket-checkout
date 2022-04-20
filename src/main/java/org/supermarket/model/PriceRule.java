package org.supermarket.model;

public record PriceRule(String item, long regularPrice, UnitPrice unitPrice) {

    public PriceRule(String item, long regularPrice) {
        this(item, regularPrice, null);
    }

    public boolean isNotUnitPriced() {
        return unitPrice == null;
    }

}
