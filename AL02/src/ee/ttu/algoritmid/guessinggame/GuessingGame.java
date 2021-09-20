package ee.ttu.algoritmid.guessinggame;

import java.util.Arrays;
import java.util.Comparator;

public class GuessingGame {

    Oracle oracle;

    public GuessingGame(Oracle oracle) {
        this.oracle = oracle;
    }

    /**
     * @param fruitArray - All the possible fruits.
     * @return the name of the fruit.
     */
    public String play(Fruit[] fruitArray) {

        Arrays.sort(fruitArray,(Comparator.comparingInt(Fruit::getWeight)));

        //1 3 5 7 11 15 18 20 21
        //0 1 2 3 4  5  6  7  8

        var low = 0;
        var high = fruitArray.length;


        var found = false;

        while (!found){
            var mid = (low + high) / 2;
            var guess = oracle.isIt(fruitArray[mid]);
            if (guess == "heavier") {
               low = mid;
            }
            else if (guess == "lighter") {
                high = mid;
            }
            else if(guess == "correct!"){
                return fruitArray[mid].getName();
            }
        }

        return "";
    }
}