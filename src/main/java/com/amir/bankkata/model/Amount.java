package com.amir.bankkata.model;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Objet valeur représentant le montant
 */
public class Amount {
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.FRANCE);
    public static final Amount ZERO = new Amount(BigDecimal.ZERO);

    private BigDecimal value;

    public Amount(BigDecimal value) {
        Preconditions.checkNotNull(value);
        this.value = value;
    }

    public Amount(double amount) {
        this(new BigDecimal(amount, MathContext.DECIMAL64).setScale(DEFAULT_CURRENCY.getDefaultFractionDigits()));
    }

    public BigDecimal getAmount() {
        return value;
    }

    public static Amount amountOf(BigDecimal value) {
        return new Amount(value);
    }

    public Amount plus(Amount otherAmount) {
        return new Amount(this.value.add(otherAmount.value));
    }

    public Amount minus(Amount otherAmount) {
        return new Amount(this.value.subtract(otherAmount.value));
    }

    /** Renvoie true uniquement si le montant egale à 0. */
    public boolean isZero() {
        return this.value.compareTo(ZERO.value) == 0;
    }

    /** Renvoie true uniquement si le montant est négatif. */
    public boolean isNegative() {
        return this.value.compareTo(ZERO.value) < 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Amount)) {
            return false;
        }
        Amount other = (Amount) obj;
        return this.value.equals(other.value);
    }

    public int compareTo(Amount amount) {
        final int EQUAL = 0;
        if(this == amount) {
            return EQUAL;
        }
        int compar = this.value.compareTo(amount.value);
        if(compar != EQUAL) {
            return compar;
        }
        return EQUAL;
    }
}
