package org.supermarket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.supermarket.exception.PriceRuleNotFound;
import org.supermarket.model.PriceRule;
import org.supermarket.model.UnitPrice;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PriceRulesTest {

    private Set<PriceRule> priceRuleSet;
    private PriceRules priceRulesUnderTest;

    @BeforeEach
    void beforeEach() {
        this.priceRuleSet = new HashSet<>();
        this.priceRulesUnderTest = PriceRules.of(priceRuleSet);

    }

    @Test
    void countTotalItemsPrice() {
        priceRuleSet.add(new PriceRule("A", 30, UnitPrice.of(2, 50)));
        priceRuleSet.add(new PriceRule("B", 10, UnitPrice.of(3, 40)));
        priceRuleSet.add(new PriceRule("C", 15));
        Map<String, Integer> itemsByAmount = Map.of("A", 5, "B", 3, "C", 2
        );

        long totalPrice = priceRulesUnderTest.countTotalItemsPrice(itemsByAmount);

        assertEquals(200L, totalPrice);
    }

    @Test
    void countTotalItemsPriceWhenNoUnitPrice() {
        priceRuleSet.add(new PriceRule("A", 20));
        priceRuleSet.add(new PriceRule("B", 15));
        priceRuleSet.add(new PriceRule("C", 10));
        Map<String, Integer> itemsByAmount = Map.of("A", 2, "B", 2, "C", 1);

        long totalPrice = priceRulesUnderTest.countTotalItemsPrice(itemsByAmount);

        assertEquals(80L, totalPrice);
    }

    @Test
    void countTotalItemsShouldReturnZeroWhenItemsAreAbsent() {
        priceRuleSet.add(new PriceRule("A", 20));
        priceRuleSet.add(new PriceRule("B", 15));
        priceRuleSet.add(new PriceRule("C", 10));

        long totalPrice = priceRulesUnderTest.countTotalItemsPrice(emptyMap());

        assertEquals(0L, totalPrice);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "A ", "D"})
    void countTotalItemsPriceShouldThrowExceptionWhenNoPriceRuleWithItemExists(String item) {
        priceRuleSet.add(new PriceRule("A", 20));
        priceRuleSet.add(new PriceRule("B", 15));
        Map<String, Integer> itemsByAmount = Map.of(item, 2);

        assertThrows(PriceRuleNotFound.class, () -> priceRulesUnderTest.countTotalItemsPrice(itemsByAmount));
    }
}