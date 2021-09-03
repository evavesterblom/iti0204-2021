package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;

public class AL01B {

    /**
     * Estimate or find the exact time required to compute the n-th Fibonacci number.
     * @param n The n-th number to compute.
     * @return The time estimate or exact time in YEARS.
     */
    public String timeToComputeRecursiveFibonacci(int n) {
        // Complexity O(2 astmes n) ---> 1.618 astmes n = x aastat
        // vaja teada kui palju n=1 puhul aega võtab
        // kui proovida fib(45) -> võttis 21.7454 sekundit  45 = log x / log1.6
        // O(45) = 21.7454 s
        // 1.618ˇ45 = 21.47
        // 1.618ˇn  = x aega
        // x * 1.618ˇ45 = 21.47 * 1.618ˇn
        // x = 21.47 * 1.618 ˇ(n-45) sekundit - kui on sisend 45.
        // x = baseTime * expBase ˇ(n-exponent)

        int base = 45;
        double expBase = 1.618;

        Instant before = Instant.now();
        BigInteger bI = recursiveF(base);
        Instant after = Instant.now();
        long baseTime = Duration.between(before, after).toSeconds();
        double computedTimeSeconds = baseTime * Math.pow(expBase,(n-base));
        double computedTimeYears = computedTimeSeconds / 31536000;
        return String.valueOf(computedTimeYears);
    }

    /**
     * Compute the Fibonacci sequence number recursively.
     * (You need this in the timeToComputeRecursiveFibonacci(int n) function!)
     * @param n The n-th number to compute.
     * @return The n-th Fibonacci number as a string.
     */
    public BigInteger recursiveF(int n) {
        if (n <= 1)
            return BigInteger.valueOf(n);
        return recursiveF(n - 1).add(recursiveF(n - 2));
    }
}