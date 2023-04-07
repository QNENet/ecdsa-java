package com.starkbank.ellipticcurve;

import java.math.BigInteger;
import java.util.*;

/**
 * Elliptic Curve Equation.
 * y^2 = x^3 + A*x + B (mod P)
 */

public class Curve {

    public BigInteger A;
    public BigInteger B;
    public BigInteger P;
    public BigInteger N;
    public Point G;
    public String name;
    public long[] oid;

    /**
     * @param A    A
     * @param B    B
     * @param P    P
     * @param N    N
     * @param Gx   Gx
     * @param Gy   Gy
     * @param name name
     * @param oid  oid
     */
    public Curve(BigInteger A, BigInteger B, BigInteger P, BigInteger N, BigInteger Gx, BigInteger Gy, String name, long[] oid) {
        this.A = A;
        this.B = B;
        this.P = P;
        this.N = N;
        this.G = new Point(Gx, Gy);
        this.name = name;
        this.oid = oid;
    }

    /**
     * Verify if the point `p` is on the curve
     *
     * @param p Point p = Point(x, y)
     * @return true if point is in the curve otherwise false
     */
    public boolean contains(Point p) {
        if (p.x.compareTo(BigInteger.ZERO) < 0) {
            return false;
        }
        if (p.x.compareTo(this.P) >= 0) {
            return false;
        }
        if (p.y.compareTo(BigInteger.ZERO) < 0) {
            return false;
        }
        if (p.y.compareTo(this.P) >= 0) {
            return false;
        }
        return p.y.pow(2).subtract(p.x.pow(3).add(A.multiply(p.x)).add(B)).mod(P).intValue() == 0;
    }

    /**
     * @return int
     */
    public int length() {
        return (1 + N.toString(16).length()) / 2;
    }

    /**
     *
     */
    public static final Curve secp256k1 = new Curve(
            BigInteger.ZERO,
            BigInteger.valueOf(7),
            new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f", 16),
            new BigInteger("fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141", 16),
            new BigInteger("79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798", 16),
            new BigInteger("483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8", 16),
            "secp256k1",
            new long[]{1, 3, 132, 0, 10}
    );

    /**
     *
     */
    public static final Curve secp384r1 = new Curve(
            new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffeffffffff0000000000000000fffffffc", 16), // A
            new BigInteger("b3312fa7e23ee7e4988e056be3f82d19181d9c6efe8141120314088f5013875ac656398d8a2ed19d2a85c8edd3ec2aef", 16), // B
            new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffeffffffff0000000000000000ffffffff", 16), // P
            new BigInteger("ffffffffffffffffffffffffffffffffffffffffffffffffc7634d81f4372ddf581a0db248b0a77aecec196accc52973", 16), // N
            new BigInteger("aa87ca22be8b05378eb1c71ef320ad746e1d3b628ba79b9859f741e082542a385502f25dbf55296c3a545e3872760ab7", 16), // Gx
            new BigInteger("3617de4a96262c6f5d9e98bf9292dc29f8f41dbd289a147ce9da3113b5f0b8c00a60b1ce1d7e819d7a431d7c90ea0e5f", 16), // Gy
            "secp384r1", // name
            new long[]{1, 3, 132, 0, 34} // oid
    );


    /**
     *
     */
    public static final List supportedCurves = new ArrayList();

    /**
     *
     */
    public static final Map curvesByOid = new HashMap();

    static {
        supportedCurves.add(secp256k1);
        supportedCurves.add(secp384r1);

        for (Object c : supportedCurves) {
            Curve curve = (Curve) c;
            curvesByOid.put(Arrays.hashCode(curve.oid), curve);
        }
    }
}
