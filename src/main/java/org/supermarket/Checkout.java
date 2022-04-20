package org.supermarket;

import org.supermarket.exception.ItemNotFoundException;
import org.supermarket.model.Price;
import org.supermarket.model.PriceRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class Checkout {
    private final PriceRules priceRules;
    private final Set<String> availableItems;
    private final Map<String, Integer> itemsByAmount = new HashMap<>();

    public Checkout(PriceRules priceRules) {
        this.priceRules = priceRules;
        this.availableItems = evaluateAvailableItems(priceRules);
    }

    private static Set<String> evaluateAvailableItems(PriceRules priceRules) {
        return priceRules.priceRuleSet()
                .stream()
                .map(PriceRule::item)
                .collect(toSet());
    }

    public void scan(String item) {
        Optional.ofNullable(item)
                .filter(availableItems::contains)
                .ifPresentOrElse(this::addItem,
                        () -> {
                            throw new ItemNotFoundException(item);
                        });
    }

    public Price total() {
        long totalPrice = priceRules.countTotalItemsPrice(itemsByAmount);
        return new Price(totalPrice);
    }

    private void addItem(String item) {
        itemsByAmount.merge(item, 1, Integer::sum);
    }
}
