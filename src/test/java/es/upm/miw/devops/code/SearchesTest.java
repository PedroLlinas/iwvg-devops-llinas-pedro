package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchesTest {

    @Test
    void testFindUserFamilyNameByUserNameDistinct() {
        assertThat(new Searches().findUserFamilyNameByUserNameDistinct("Paula").toList())
                .containsExactly("Torres");
    }

    @Test
    void testFindUserFractionNumeratorByFamilyName() {
        assertThat(new Searches().findFractionNumeratorByUserFamilyName("Torres").toList())
                .containsExactly(2, 4, 0, 1, 1);
    }

    @Test
    void testFindFamilyNameByFractionDenominator() {
        assertThat(new Searches().findUserFamilyNameByFractionDenominator(2).toList())
                .containsExactly("López", "Torres");
    }

    @Test
    void testFindFractionDivisionByUserId() {
        assertThat(new Searches().findFractionDivisionByUserId("2"))
                .isNotNull()
                .returns(-15, Fraction::getNumerator)
                .returns(1, Fraction::getDenominator);
    }

    @Test
    void testFindDecimalFractionByNegativeSignFraction() {
        // Con el poblador dado, las fracciones negativas son: -1/5 y 3/-6 → [-0.2, -0.5]
        assertThat(new Searches().findDecimalFractionByNegativeSignFraction().toList())
                .containsExactly(-0.2, -0.5);
    }
}
