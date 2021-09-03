package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01A {
    /**
     * Compute the Fibonacci sequence number.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public String iterativeF(int n) {

        BigInteger nn = BigInteger.valueOf(n);

        if (nn.equals(BigInteger.ZERO)) return "0";

        BigInteger A = BigInteger.ONE;
        BigInteger B = BigInteger.ONE;
        BigInteger C;

        for (BigInteger counter = BigInteger.valueOf(3);
                counter.compareTo(nn) <= 0;
                counter = counter.add(BigInteger.ONE)){

                C = A.add(B);
                A = B;
                B = C;
        }
        return String.valueOf(B);
    }

    /**
     * Compute the Fibonacci sequence number.
     * @param n The number of the sequence to compute.
     * @return The n-th number in Fibonacci series.
     */
    public String iterativeFun(int n) {

        if (n == 0) return "0";

        int a = 1;
        int b = 1;
        int c;

        for (int i = 3; i <= n; i++){

            c = a + b;
            a = b;
            b = c;
        }
        return String.valueOf(b);
    }

}



