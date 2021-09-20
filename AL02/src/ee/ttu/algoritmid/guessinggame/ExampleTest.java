package ee.ttu.algoritmid.guessinggame;

import java.util.Arrays;

public class ExampleTest {
    public static final void testExampleFruitArray() throws Exception {
        Fruit[] array = {new Fruit("Apelsin", 150),
                new Fruit("Banaan", 185),
                new Fruit("Greip", 250),
                new Fruit("Mango", 210),
                new Fruit("Pirn", 170),
                new Fruit("Ploom", 50),
                new Fruit("Virsik", 130),
                new Fruit("Õun", 110)
        };
        final Fruit correct = array[1];
        Oracle oracle = new Oracle(correct);
        GuessingGame nm = new GuessingGame(oracle);
        Fruit[] copy = Arrays.copyOf(array, array.length);
        final boolean guessResult = nm.play(copy).equals(correct.getName());
        if (!guessResult) {
            Assert.fail("Your solution did not guess correctly.");
        }
    }

    public static void main(String[] args) {
        try {
            testExampleFruitArray();
        } catch (Exception ignored) {
        }
    }
}