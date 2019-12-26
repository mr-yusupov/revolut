package com.revolut.money.domain.money;

import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;
import static org.junit.Assert.assertEquals;

public class MoneyTest {

    @Test(expected = IllegalArgumentException.class)
    public void moneyWithNegativeAmountCanNotBeCreated() {
        new Money(TEN.negate());
    }

    @Test
    public void shouldAddTwoMoneyAmounts() {
        Money money = new Money(ZERO);
        Money extraMoney = new Money(TEN);

        Money result = money.add(extraMoney);

        BigDecimal expectedMoneyAmount = ZERO.add(TEN);
        assertEquals(result, new Money(expectedMoneyAmount));
    }

    @Test
    public void shouldSubtractWhenResultAmountIsPositive() {
        Money money = new Money(TEN);
        Money subtractMoney = new Money(ONE);

        Money result = money.subtract(subtractMoney);

        BigDecimal expectedMoneyAmount = TEN.subtract(ONE);
        assertEquals(result, new Money(expectedMoneyAmount));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionWhenResultAmountOfSubtractionIsNegative() {
        Money money = new Money(ONE);
        Money subtractMoney = new Money(TEN);

        money.subtract(subtractMoney);
    }
}
