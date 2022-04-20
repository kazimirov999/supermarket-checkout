package org.supermarket;


import org.supermarket.model.Price;
import org.supermarket.model.PriceRule;
import org.supermarket.model.UnitPrice;

import java.util.HashSet;
import java.util.Set;

public class App {

    private static final PriceRules PRICE_RULES;

    static {
        Set<PriceRule> priceRuleSet = new HashSet<>();
        priceRuleSet.add(new PriceRule("A", 50, UnitPrice.of(3, 130)));
        priceRuleSet.add(new PriceRule("B", 30, UnitPrice.of(2, 45)));
        priceRuleSet.add(new PriceRule("C", 20));
        priceRuleSet.add(new PriceRule("D", 15));
        PRICE_RULES = PriceRules.of(priceRuleSet);
    }

    public static void main(String[] args) {
        Checkout checkout = new Checkout(PRICE_RULES);
        checkout.scan("A");
        checkout.scan("D");
        checkout.scan("A");
        checkout.scan("C");
        checkout.scan("C");
        checkout.scan("A");
        checkout.scan("A");
        checkout.scan("B");
        checkout.scan("B");
        checkout.scan("B");
        checkout.scan("B");

        Price price = checkout.total();

        System.out.println("Total price is: " + price.totalPrice()); //Total price is: 325
    }
}
