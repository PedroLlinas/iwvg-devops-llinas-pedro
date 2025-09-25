package es.upm.miw.devops.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private User user;

    @BeforeEach
    void before() {
        user = new User("1", "Pedro", "Llinás", new ArrayList<>());
    }

    @Test
    void testUserConstructor() {
        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getName()).isEqualTo("Pedro");
        assertThat(user.getFamilyName()).isEqualTo("Llinás");
        assertThat(user.getFractions()).isEmpty();
    }

    @Test
    void testUserEmptyConstructor() {
        user = new User();
        assertThat(user.getFractions()).isEmpty();
        assertThat(user.getName()).isNull();
        assertThat(user.getFamilyName()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        user.setName("Juan");
        user.setFamilyName("Pérez");
        List<Fraction> fractions = new ArrayList<>();
        fractions.add(new Fraction(1, 2));
        user.setFractions(fractions);

        assertThat(user.getName()).isEqualTo("Juan");
        assertThat(user.getFamilyName()).isEqualTo("Pérez");
        assertThat(user.getFractions()).hasSize(1);
    }

    @Test
    void testAddFraction() {
        user.addFraction(new Fraction(1, 2));
        user.addFraction(new Fraction(3, 4));

        assertThat(user.getFractions())
                .hasSize(2)
                .extracting(Fraction::toString)
                .containsExactly("Fraction{numerator=1, denominator=2}", "Fraction{numerator=3, denominator=4}");
    }

    @Test
    void testFullName() {
        assertThat(user.fullName()).isEqualTo("Pedro Llinás");
    }

    @Test
    void testInitials() {
        assertThat(user.initials()).isEqualTo("P.");
    }

    @Test
    void testToString() {
        user.addFraction(new Fraction(1, 2));
        String result = user.toString();

        assertThat(result)
                .contains("id='1'")
                .contains("name='Pedro'")
                .contains("familyName='Llinás'")
                .contains("fractions=[Fraction{numerator=1, denominator=2}]");
    }
}
