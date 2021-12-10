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
        solution.friendly("U");
        solution.talkedToEachOther("U", "V");
        var actual = solution.memberOfNetwork("V");

        assertEquals(AL07.Network.FRIENDLY, actual);
    }

    @Test
    void testFriendlyPersonTalksToStranger_BecomesFriendly2(){
        AL07 solution = new AL07();
        solution.addPerson("U");
        solution.addPerson("V");
        solution.friendly("U");
        solution.talkedToEachOther("U", "V");
        var actual = solution.memberOfNetwork("V");

        assertEquals(AL07.Network.FRIENDLY, actual);
    }


    @Test
    void testFriendlyPersons(){
        AL07 solution = new AL07();
        solution.addPerson("U");
        solution.addPerson("V");
        solution.addPerson("UU");
        solution.addPerson("VV");
        solution.friendly("U");
        var actual = solution.memberOfNetwork("VV");

        assertEquals(AL07.Network.UNKNOWN, actual);
    }

    @Test
    void testFriendly_NoAdd(){
        AL07 solution = new AL07();
        solution.friendly("P");
        var actual = solution.memberOfNetwork("P");

        assertEquals(AL07.Network.FRIENDLY, actual);
    }

    @Test
    void testUnFriendly_NoAdd(){
        AL07 solution = new AL07();
        solution.unfriendly("UN");
        var actual = solution.memberOfNetwork("UN");

        assertEquals(AL07.Network.UNFRIENDLY, actual);
    }

}