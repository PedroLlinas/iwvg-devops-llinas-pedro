package es.upm.miw.devops.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

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
}
