package ee.ttu.algoritmid.bond;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AL07Test {
    @Test
    void testAddFriendlyPerson(){
        AL07 solution = new AL07();
        solution.addPerson("U");
        solution.friendly("U");
        var actual = solution.memberOfNetwork("U");

        assertEquals(AL07.Network.FRIENDLY, actual);
    }

    @Test
    void testFriendlyPersonTalksToStranger_BecomesFriendly(){
        AL07 solution = new AL07();
        solution.addPerson("U");
        //solution.addPerson("V");
        solution.friendly("U");
        solution.talkedToEachOther("U", "V");
        var actual = solution.memberOfNetwork("V");

        assertEquals(AL07.Network.FRIENDLY, actual);
    }

}