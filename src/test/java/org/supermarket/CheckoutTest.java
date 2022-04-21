package org.supermarket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.supermarket.exception.ItemNotFoundException;
import org.supermarket.model.Price;
import org.supermarket.model.PriceRule;
import org.supermarket.model.UnitPrice;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CheckoutTest {

    private PriceRules priceRules;
    private Checkout checkoutUnderTest;

    @BeforeEach
    void beforeEach() {
        this.priceRules = Mockito.mock(PriceRules.class);
        Set<PriceRule> priceRuleSet = Set.of(
                new PriceRule("A", 30, UnitPrice.of(2, 50)),
                new PriceRule("B", 10, UnitPrice.of(3, 40)),
                new PriceRule("C", 15)
        );
        Map<String, Integer> itemsByAmount = Map.of("A", 2, "B", 1, "C", 1);
        when(priceRules.priceRuleSet()).thenReturn(priceRuleSet);
        when(priceRules.countTotalItemsPrice(itemsByAmount)).thenReturn(5L);

        this.checkoutUnderTest = new Checkout(priceRules);
    }

    @Test
    void total() {
        checkoutUnderTest.scan("A");
        checkoutUnderTest.scan("B");
        checkoutUnderTest.scan("A");
        checkoutUnderTest.scan("C");

        assertEquals(new Price(5), checkoutUnderTest.total());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "A ", "D"})
    void scanShouldThrowExceptionWhenNoPriceRuleWithItemExists(String item) {

        assertThrows(ItemNotFoundException.class, () -> checkoutUnderTest.scan(item));
    }
}