package com.company;

/**
 * This class stores a complex number, and allows the user to do arithmetic
 * with these numbers.
 *
 * Note that our complex numbers are immutable. That is, once they are 
 * constructed, they will not change. In particular, all our algebraic 
 * operations create a new complex number rather than updating the current one.
 *
 * @author W. Patrick Hooper
 */
public final class Complex {
    // The number stored is x+I*y.
    final private double x, y;
    // I don't want to allow anyone to access these numbers so I've labeled
    // them private. 

    /**
     * Construct a point from real and imaginary parts.
     */
    public Complex(double real_part, double imaginary_part) {
        x = real_part;
        y = imaginary_part;
    }

    /**
     * Construct a real number.
     */
    public Complex(double real_part) {
        x = real_part;
        y = 0;
    }

    // A static constructor.

    /**
     * Construct a complex number from the given polar coordinates.
     */
    public static Complex fromPolar(double r, double theta) {
        return new Complex(r * Math.cos(theta), r * Math.sin(theta));
    }

    // Basic operations on Complex numbers. 

    /**
     * Return the real part.
     */
    public double re() {
        return x;
    }

    /**
     * Return the imaginary part.
     */
    public double im() {
        return y;
    }

    /**
     * Return the complex conjugate
     */
    public Complex conj() {
        return new Complex(x, -y);
    }

    /**
     * Return the square of the absolute value.
     */
    public double absSquared() {
        return x * x + y * y;
    }

    /**
     * Return the absolute value.
     */
    public double abs() {
        // The java.lang.Math package contains many useful mathematical functions,
        // including the square root function.
        return Math.sqrt(absSquared());
    }

    // ARITHMETIC

    /**
     * Add a complex number to this one.
     *
     * @param z The complex number to be added.
     * @return A new complex number which is the sum.
     */
    public Complex add(Complex z) {
        return new Complex(x + z.x, y + z.y);
    }

    /**
     * Subtract a complex number from this one.
     *
     * @param z The complex number to be subtracted.
     * @return A new complex number which is the sum.
     */
    public Complex minus(Complex z) {
        return new Complex(x - z.x, y - z.y);
    }

    /**
     * Negate this complex number.
     *
     * @return The negation.
     */
    public Complex neg() {
        return new Complex(-x, -y);
    }

    /**
     * Compute the product of two complex numbers
     *
     * @param z The complex number to be multiplied.
     * @return A new complex number which is the product.
     */
    public Complex mult(Complex z) {
        return new Complex(x * z.x - y * z.y, x * z.y + z.x * y);
    }

    /**
     * Divide this complex number by a real number.
     *
     * @param q The number to divide by.
     * @return A new complex number representing the quotient.
     */
    public Complex div(double q) {
        return new Complex(x / q, y / q);
    }

    /**
     * Return the multiplicative inverse.
     */
    public Complex inv() {
        // find the square of the absolute value of this complex number.
        double abs_squared = absSquared();
        return new Complex(x / abs_squared, -y / abs_squared);
    }

    /**
     * Compute the quotient of two complex numbers.
     *
     * @param z The complex number to divide this one by.
     * @return A new complex number which is the quotient.
     */
    public Complex div(Complex z) {
        return mult(z.inv());
    }

    /**
     * Return the complex exponential of this complex number.
     */
    public Complex exp() {
        return new Complex(Math.exp(x) * Math.cos(y), Math.exp(x) * Math.sin(y));
    }


    // FUNCTIONS WHICH KEEP JAVA HAPPY:    

    /**
     * Returns this point as a string.
     * The main purpose of this function is for printing the string out,
     * so we return a string in a (fairly) human readable format.
     */
    // The _optional_ override directive "@Override" below just says we are 
    // overriding a function defined in a parent class. In this case, the 
    // parent is java.lang.Object. All classes in Java have the Object class 
    // as a superclass.
    @Override
    public String toString() {
        // Comments: 
        // 1) "" represents the empty string.
        // 2) If you add something to a string, it converts the thing you
        // are adding to a string, and then concatentates it with the string.

        // We do some voodoo to make sure the number is displayed reasonably.
        if (y == 0) {
            return "" + x;
        }
        if (y > 0) {
            return "" + x + "+" + y + "*I";
        }
        // otherwise y<0.
        return "" + x + "-" + (-y) + "*I";
    }

    /**
     * Return true if the object is a complex number which is equal to this complex number.
     */
    @Override
    public boolean equals(Object obj) {
        // Return false if the object is null
        if (obj == null) {
            return false;
        }
        // Return false if the object is not a Complex number
        if (!(obj instanceof Complex)) {
            return false;
        }

        // Now the object must be a Complex number, so we can convert it to a 
        // Complex number.
        Complex other = (Complex) obj;

        // If the x-coordinates are not equal, then return false.
        if (x != other.x) {
            return false;
        }
        // If the y-coordinates are not equal, then return false.
        if (y != other.y) {
            return false;
        }
        // Both parts are equal, so return true.
        return true;
    }

    // Remark: In Java, we should really override the hashcode function
    // whenever we override the equals function. But, I don't want to 
    // get into this for a light introduction to programming in java.
    // Hash codes are necessary for various of Java's collections. See HashSet for instance. 
    // The following was generated by Netbeans.
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
}