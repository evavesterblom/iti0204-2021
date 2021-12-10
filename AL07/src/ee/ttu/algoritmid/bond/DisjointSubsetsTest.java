package ee.ttu.algoritmid.bond;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DisjointSubsetsTest {

    @Test
    void testAddSubset() {
        DisjointSubsets ds = new DisjointSubsets();

        List<String> actual = new ArrayList<>();
        List<String> expected = Arrays.asList("1", "2", "3", "4");

        ds.addSubset("1");
        actual.add(ds.find("1"));
        ds.addSubset("2");
        actual.add(ds.find("2"));
        ds.addSubset("3");
        actual.add(ds.find("3"));
        ds.addSubset("4");
        actual.add(ds.find("4"));

        assertTrue(actual.containsAll(expected));

    }

    @Test
    void testCorrectParent() {
        DisjointSubsets ds = new DisjointSubsets();

        ds.addSubset("1");
        ds.addSubset("2");
        ds.addSubset("3");
        ds.addSubset("4");
        ds.addSubset("5");

        ds.union("4", "3");
        ds.union("2", "1");
        ds.union("1", "3");

        assertEquals("3", ds.find("1"));
        assertEquals("3", ds.find("2"));
        assertEquals("3", ds.find("3"));
        assertEquals("3", ds.find("4"));
        assertEquals("5", ds.find("5"));
    }


}