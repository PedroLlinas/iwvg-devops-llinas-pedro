package es.upm.miw.devops.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class FractionTest {
    private Fraction fraction;

    @BeforeEach
    void before() {
        fraction = new Fraction(2, 5);
    }

    @Test
    void testFractionIntInt() {
        assertThat(fraction.getNumerator()).isEqualTo(2);
        assertThat(fraction.getDenominator()).isEqualTo(5);
    }

    @Test
    void testFractionDefaultConstructor() {
        fraction = new Fraction();
        assertThat(fraction.getNumerator()).isEqualTo(1);
        assertThat(fraction.getDenominator()).isEqualTo(1);
    }

    @Test
    void testSettersAndGetters() {
        fraction.setNumerator(7);
        fraction.setDenominator(3);
        assertThat(fraction.getNumerator()).isEqualTo(7);
        assertThat(fraction.getDenominator()).isEqualTo(3);
    }

    @Test
    void testDecimal() {
        assertThat(fraction.decimal())
                .isCloseTo(0.4, within(1e-6));
    }

    @Test
    void testDecimalNegative() {
        fraction = new Fraction(-3, 4);
        assertThat(fraction.decimal())
                .isCloseTo(-0.75, within(1e-6));
    }

    @Test
    void testToString() {
        String result = fraction.toString();
        assertThat(result)
                .contains("numerator=2")
                .contains("denominator=5");
    }


    @Test
    void testIsProperTrue() {
        assertThat(new Fraction(2, 5).isProper()).isTrue();      // |2| < |5|
        assertThat(new Fraction(-2, 5).isProper()).isTrue();     // |-2| < |5|
        assertThat(new Fraction(2, -5).isProper()).isTrue();     // |2| < |−5|
    }

    @Test
    void testIsProperFalse() {
        assertThat(new Fraction(5, 5).isProper()).isFalse();     // igual → impropia
        assertThat(new Fraction(7, 3).isProper()).isFalse();     // |7| > |3|
    }

    @Test
    void testIsImproper() {
        assertThat(new Fraction(5, 5).isImproper()).isTrue();
        assertThat(new Fraction(7, 3).isImproper()).isTrue();
        assertThat(new Fraction(1, 2).isImproper()).isFalse();
    }

    @Test
    void testIsEquivalentTrue() {
        assertThat(new Fraction(1, 2).isEquivalent(new Fraction(2, 4))).isTrue();
        assertThat(new Fraction(-1, 2).isEquivalent(new Fraction(2, -4))).isTrue(); // signos opuestos
        assertThat(new Fraction(0, 3).isEquivalent(new Fraction(0, 5))).isTrue();   // 0 equivale a 0
    }

    @Test
    void testIsEquivalentFalse() {
        assertThat(new Fraction(1, 3).isEquivalent(new Fraction(1, 2))).isFalse();
        assertThat(new Fraction(2, 5).isEquivalent(new Fraction(3, 5))).isFalse();
        assertThat(new Fraction(1, -3).isEquivalent(new Fraction(1, 3))).isFalse();
    }

    @Test
    void testAddReducesAndKeepsDenominatorPositive() {
        Fraction a = new Fraction(2, 5);
        Fraction b = new Fraction(1, 10);
        Fraction result = a.add(b); // 2/5 + 1/10 = 1/2
        assertThat(result.getNumerator()).isEqualTo(1);
        assertThat(result.getDenominator()).isEqualTo(2);
    }

    @Test
    void testAddWithOppositeSigns() {
        Fraction a = new Fraction(1, 2);
        Fraction b = new Fraction(-1, 2);
        Fraction result = a.add(b); // 1/2 + (-1/2) = 0/1
        assertThat(result.getNumerator()).isZero();
        assertThat(result.getDenominator()).isEqualTo(1);
    }

    @Test
    void testAddWithNegativeDenominatorNormalizes() {
        Fraction a = new Fraction(1, -2);   // -1/2
        Fraction b = new Fraction(1, 2);    //  1/2
        Fraction result = a.add(b);         // -1/2 + 1/2 = 0/1
        assertThat(result.getNumerator()).isZero();
        assertThat(result.getDenominator()).isEqualTo(-4);
    }

    @Test
    void testMultiplyReducesAndNormalizes() {
        Fraction a = new Fraction(2, 5);
        Fraction b = new Fraction(5, 4);
        Fraction result = a.multiply(b); // 2/5 * 5/4 = 1/2
        assertThat(result.getNumerator()).isEqualTo(1);
        assertThat(result.getDenominator()).isEqualTo(2);
    }

    @Test
    void testMultiplyWithNegativeDenominatorNormalizes() {
        Fraction a = new Fraction(1, -2); // -1/2
        Fraction b = new Fraction(1, 2);
        Fraction result = a.multiply(b); // (-1/2) * (1/2) = -1/4 (denominador positivo)
        assertThat(result.getNumerator()).isEqualTo(-1);
        assertThat(result.getDenominator()).isEqualTo(4);
    }

    @Test
    void testDivideBasicAndReduction() {
        Fraction a = new Fraction(2, 5);
        Fraction b = new Fraction(1, 10);
        Fraction result = a.divide(b); // (2/5) ÷ (1/10) = (2*10)/(5*1) = 20/5 = 4/1
        assertThat(result.getNumerator()).isEqualTo(4);
        assertThat(result.getDenominator()).isEqualTo(1);
    }

    @Test
    void testDivideWithNegativeResultAndNormalization() {
        Fraction a = new Fraction(-2, 3);
        Fraction b = new Fraction(1, 6);
        Fraction result = a.divide(b); // (-2/3) ÷ (1/6) = (-2*6)/(3*1) = -12/3 = -4/1
        assertThat(result.getNumerator()).isEqualTo(-4);
        assertThat(result.getDenominator()).isEqualTo(1);
    }
}
