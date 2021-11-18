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
        //jama var actual = unitUnderTest.registerToFlight(participant);
        //assertNull(actual);
    }

    @Test
    public void a(){
        //var errorParticipant1 = new Double[]{109.76864722775014, 103.76732287213353, 100.35650214518184};
        //var err1 = new TestFlightCrewMember(FlightCrewMember.Role.COPILOT, "  ", -4.999);
        var participant1 = new TestFlightCrewMember(FlightCrewMember.Role.COPILOT, "Kati Karau", 13.3);
        var participant2 = new TestFlightCrewMember(FlightCrewMember.Role.COPILOT, "Kati Karau", 13.3);
        var participant3 = new TestFlightCrewMember(FlightCrewMember.Role.PILOT, "Kati Karau", 13.3);
        var participant4 = new TestFlightCrewMember(FlightCrewMember.Role.FLIGHT_ATTENDANT, "Kati Karau", 13.3);
        var participant5 = new TestFlightCrewMember(FlightCrewMember.Role.FLIGHT_ATTENDANT, "Liis", 1.3);

        unitUnderTest.registerToFlight(participant1);
        unitUnderTest.registerToFlight(participant2);
        unitUnderTest.registerToFlight(participant3);
        unitUnderTest.registerToFlight(participant4);
        unitUnderTest.registerToFlight(participant5);

        var a = unitUnderTest.crewMembersWithoutTeam();

    }



//    @Override
//    protected void tearDown() throws Exception {
//
//    }

}
