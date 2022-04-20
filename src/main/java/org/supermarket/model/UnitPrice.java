package org.supermarket.model;

public record UnitPrice(int unitSize, long price) {

    public static UnitPrice of(int unitSize, long price) {
        return new UnitPrice(unitSize, price);
    }
}
