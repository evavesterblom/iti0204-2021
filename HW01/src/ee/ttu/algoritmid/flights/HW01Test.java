package ee.ttu.algoritmid.flights;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HW01Test {
    private class TestFlightCrewMember implements FlightCrewMember{
        private Role role;
        private String name;
        private double workExperience;

        public TestFlightCrewMember(Role role, String name, Double workExperience){
            this.role = role;
            this.name = name;
            this.workExperience = workExperience;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Role getRole() {
            return role;
        }

        @Override
        public double getWorkExperience() {
            return workExperience;
        }
    }

    HW01 unitUnderTest;

    @Before
    public void setUp(){
        unitUnderTest = new HW01();
    }

    @Test
    public void registerToFLight_NotPossible_NotEnoughParticipants(){
        var participant = new TestFlightCrewMember(FlightCrewMember.Role.PILOT, "Kati Karu", 13.9);
        var actual = unitUnderTest.registerToFlight(participant);
        assertNull(actual);
    }


//    @Override
//    protected void tearDown() throws Exception {
//
//    }

}
