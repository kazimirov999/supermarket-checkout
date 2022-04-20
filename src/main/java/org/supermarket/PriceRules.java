package org.supermarket;

import org.supermarket.exception.PriceRuleNotFound;
import org.supermarket.model.PriceRule;

import java.util.Map;
import java.util.Set;

public class PriceRules {

    private final Set<PriceRule> priceRuleSet;

    private PriceRules(Set<PriceRule> priceRuleSet) {
        this.priceRuleSet = priceRuleSet;
    }

    public static PriceRules of(Set<PriceRule> priceRules) {
        return new PriceRules(priceRules);
    }

    public long countTotalItemsPrice(Map<String, Integer> itemsByAmount) {
        return itemsByAmount.entrySet()
                .stream()
                .map(entry -> countItemPrice(entry.getKey(), entry.getValue()))
                .reduce(Long::sum)
                .orElse(0L);
    }

    public Set<PriceRule> priceRuleSet() {
        return priceRuleSet;
    }

    private long countItemPrice(String item, Integer amountOfItems) {
        PriceRule priceRule = findPriceRule(item);
        if (priceRule.isNotUnitPriced()) {
            return amountOfItems * priceRule.regularPrice();
        }
        long regularPrice = calculateRegularPrice(amountOfItems, priceRule);
        long unitPrice = calculateUnitPrice(amountOfItems, priceRule);
        return Math.addExact(unitPrice, regularPrice);
    }

    private long calculateRegularPrice(Integer amountOfItems, PriceRule priceRule) {
        int unitSize = priceRule.unitPrice().unitSize();
        int amountOfItemsWithRegularPrice = amountOfItems % unitSize;
        return amountOfItemsWithRegularPrice * priceRule.regularPrice();
    }

    private long calculateUnitPrice(Integer amountOfItems, PriceRule priceRule) {
        int unitSize = priceRule.unitPrice().unitSize();
        long unitPrice = priceRule.unitPrice().price();
        return (amountOfItems / unitSize) * unitPrice;
    }

    private PriceRule findPriceRule(String item) {
        return priceRuleSet.stream()
                .filter(priceRule -> item.equals(priceRule.item()))
                .findFirst()
                .orElseThrow(() -> new PriceRuleNotFound(item));
    }
}
