package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class Run {
    public static void main(String[] args) {
        AL01A a = new AL01A();

        for(int i=0; i<=15; i++) {
            System.out.println("fib(" + i + ")= " + a.iterativeF(i) + " ");
        }
    }
}
