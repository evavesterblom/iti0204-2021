package ee.ttu.algoritmid.interestingstamps;

import java.util.ArrayList;
import java.util.List;

public class InterestingStamps {
    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {

        if (stampOptions.size() == 0) throw new IllegalArgumentException("Illegal input");

        List<Integer> interestingStamps = new ArrayList<>();
        List<Integer> boringStamps = new ArrayList<>();

        for (var stamp : stampOptions) {
            if (stamp > sum) break;
            if(stamp == 1 || Math.floorMod(stamp, 10) == 0) boringStamps.add(stamp);
            else {interestingStamps.add(stamp);}
        }

        //Collections.sort(interestingStamps, Collections.reverseOrder());
        //Collections.sort(boringStamps);

        var M = new int[sum+1];
        var V = new int[sum+1];

        for (int i = 1; i < sum+1; i++){

            M[i] = Integer.MAX_VALUE;

            for (Integer stamp : interestingStamps) {
                if (i >= stamp && M[i] > M[i - stamp] + 1) {
                    M[i] = M[i - stamp] + 1;
                    V[i] = stamp;
                }
            }

            for (Integer stamp : boringStamps) {
                if (i >= stamp && M[i] > M[i - stamp] + 1) {
                    M[i] = M[i - stamp] + 1;
                    V[i] = stamp;
                }
            }
        }

        return printStamps(sum, V);
    }

    private static List<Integer> printStamps(int n, int[] V){

        List<Integer> result = new ArrayList<>();
        while (n > 0){
            result.add(V[n]);
            n = n - V[n];
        }
        return result;
    }

    public static void main(String[] args) {

        var sum = 100;
        var stampOptions = new ArrayList<Integer>();
        stampOptions.add(1);
        stampOptions.add(10);
        stampOptions.add(24);
        stampOptions.add(30);
        stampOptions.add(33);
        stampOptions.add(36);
        //List<Integer> stamps = Arrays.asList(36, 1, 2, 8, 17, 13, 7, 15, 21, 32, 24, 26, 33, 10, 27);
        //        int sum = 100;

        var a = findStamps(sum, stampOptions);
        var b = "stop";

    }
}