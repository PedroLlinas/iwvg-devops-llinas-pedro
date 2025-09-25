package es.upm.miw.devops.code;

/**
 * Conceptos: Las fracciones propias son aquellas cuyo numerador es menor que el denominador
 * <p>
 * Las fracciones impropias son aquellas cuyo numerador es mayor que el denominador
 * <p>
 * Dos fracciones son equivalentes cuando el producto de extremos (numerador de la primera por denominador de la segunda) es igual al
 * producto de medios (denominador de la primera por el numerador de la segunda)
 * <p>
 * Las fracciones irreducibles son aquellas que no se pueden simplificar, esto sucede cuando el numerador y el denominador son primos entre
 * sí
 * <p>
 * Reducir varias fracciones a común denominador consiste en convertirlas en otras equivalentes que tengan el mismo denominador
 * <p>
 * Comparar fracciones
 * <p>
 * Suma fracciones: En primer lugar se reducen los denominadores a común denominador, y se suman o se restan los numeradores de las
 * fracciones equivalentes obtenidas
 * <p>
 * Multiplicación: La multiplicación de dos fracciones es otra fracción que tiene: Por numerador el producto de los numeradores. Por
 * denominador el producto de los denominadores.
 * <p>
 * La división de dos fracciones es otra fracción que tiene: Por numerador el producto de los extremos. Por denominador el producto de los
 * medios. Invertir fraccion
 */
public class Fraction {

    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction() {
        this(1, 1);
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public double decimal() {
        return (double) numerator / denominator;
    }

    /** Fracción propia: |numerator| < |denominator| (convención habitual, independientemente del signo). */
    public boolean isProper() {
        if (this.denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero to check proper/improper fraction");
        }
        return Math.abs(this.numerator) < Math.abs(this.denominator);
    }

    /** Fracción impropia: |numerator| >= |denominator|. */
    public boolean isImproper() {
        if (this.denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero to check proper/improper fraction");
        }
        return Math.abs(this.numerator) >= Math.abs(this.denominator);
    }

    /**
     * Equivalencia: a/b ≡ c/d  ⇔  a*d == b*c (cruzados).
     * Permite denominadores negativos; no modifica los operandos.
     */
    public boolean isEquivalent(Fraction other) {
        if (other == null) return false;
        // manejar casos con cero para evitar 0 * Integer.MIN_VALUE overflow improbable
        long left = (long) this.numerator * (long) other.denominator;
        long right = (long) this.denominator * (long) other.numerator;
        return left == right;
    }

    /** Suma: a/b + c/d = (a*d + b*c) / (b*d), resultado simplificado y con denominador positivo. */
    public Fraction add(Fraction other) {
        if (other == null) throw new IllegalArgumentException("Other fraction cannot be null");
        long num = (long) this.numerator * (long) other.denominator
                + (long) this.denominator * (long) other.numerator;
        long den = (long) this.denominator * (long) other.denominator;
        return normalizeAndReduce(num, den);
    }

    /** Multiplicación: (a/b) * (c/d) = (a*c) / (b*d), resultado simplificado y con denominador positivo. */
    public Fraction multiply(Fraction other) {
        if (other == null) throw new IllegalArgumentException("Other fraction cannot be null");
        long num = (long) this.numerator * (long) other.numerator;
        long den = (long) this.denominator * (long) other.denominator;
        return normalizeAndReduce(num, den);
    }

    /**
     * División: (a/b) ÷ (c/d) = (a*d) / (b*c).
     * Lanza ArithmeticException si el numerador de la otra fracción es 0 (división por 0).
     */
    public Fraction divide(Fraction other) {
        if (other == null) throw new IllegalArgumentException("Other fraction cannot be null");
        if (other.numerator == 0) {
            throw new ArithmeticException("Cannot divide by a fraction with zero numerator");
        }
        long num = (long) this.numerator * (long) other.denominator;
        long den = (long) this.denominator * (long) other.numerator;
        return normalizeAndReduce(num, den);
    }

    /** Normaliza el signo al denominador y reduce con el MCD. */
    private static Fraction normalizeAndReduce(long num, long den) {
        if (den == 0) {
            throw new ArithmeticException("Resulting denominator is zero");
        }
        // trasladar el signo al numerador
        if (den < 0) {
            num = -num;
            den = -den;
        }
        long g = gcd(Math.abs(num), Math.abs(den));
        num /= g;
        den /= g;

        // comprobación de rango para volver a int (muy raro que se desborde con entradas razonables)
        if (num < Integer.MIN_VALUE || num > Integer.MAX_VALUE || den < Integer.MIN_VALUE || den > Integer.MAX_VALUE) {
            throw new ArithmeticException("Overflow reducing result to int");
        }
        return new Fraction((int) num, (int) den);
    }

    /** MCD por Euclides sobre long. */
    private static long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a == 0 ? 1 : Math.abs(a);
    }

    @Override
    public String toString() {
        return "Fraction{" +
                "numerator=" + numerator +
                ", denominator=" + denominator +
                '}';
    }
}
