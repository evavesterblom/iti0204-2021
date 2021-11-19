package ee.ttu.algoritmid.flights;

import org.junit.Before;
import org.junit.Test;

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

    HW01 crewMemberSystemUnit;

    @Before
    public void setUp(){
        crewMemberSystemUnit = new HW01();
    }

    @Test
    public void testEmptyQueue(){
        var actual = crewMemberSystemUnit.crewMembersWithoutTeam();
        assertTrue(actual.isEmpty());
    }
    @Test
    public void testSingleMember(){
        testSingleCrewMember(FlightCrewMember.Role.PILOT);
        testSingleCrewMember(FlightCrewMember.Role.COPILOT);
        testSingleCrewMember(FlightCrewMember.Role.FLIGHT_ATTENDANT);
    }

    @Test
    public void testAvailableCrewMemberList_SeniorityOrderFromSmallest(){
        var crewMemberSystem = new HW01();
        var participant1 = new TestFlightCrewMember(FlightCrewMember.Role.FLIGHT_ATTENDANT, "Kati", 13.3444444444444);
        var participant2 = new TestFlightCrewMember(FlightCrewMember.Role.PILOT, "Kati", 1.9);
        crewMemberSystem.registerToFlight(participant1);
        crewMemberSystem.registerToFlight(participant2);

        var actual = crewMemberSystem.crewMembersWithoutTeam();

        assertTrue(actual.size() == 2);
        assertEquals(1.9, actual.get(0).getWorkExperience());
        assertEquals(13.3444444444444, actual.get(1).getWorkExperience());
    }

    @Test
    public void testAvailableCrewMemberListRoleOrder_WhenSenioritySame(){
        addAndRegisterSingleCrewMember(FlightCrewMember.Role.PILOT, "name", 0.1);
        addAndRegisterSingleCrewMember(FlightCrewMember.Role.FLIGHT_ATTENDANT,"name", 0.1);
        addAndRegisterSingleCrewMember(FlightCrewMember.Role.COPILOT,"name", 0.1);

        var actual = crewMemberSystemUnit.crewMembersWithoutTeam();

        assertTrue(actual.size() == 3);
        assertEquals(FlightCrewMember.Role.FLIGHT_ATTENDANT, actual.get(0).getRole());
        assertEquals(FlightCrewMember.Role.COPILOT, actual.get(1).getRole());
        assertEquals(FlightCrewMember.Role.PILOT, actual.get(2).getRole());
    }

    @Test
    public void testReturnCrewAndRemoveFromQueue_WhenMatchAvailable(){
        //13 - 5 - 1
        var flightCrew = addAndRegisterSingleCrewMember(FlightCrewMember.Role.PILOT, "Pilot", 13.0);
        assertNull(flightCrew);

        flightCrew = addAndRegisterSingleCrewMember(FlightCrewMember.Role.FLIGHT_ATTENDANT, "Attendant", 1.0);
        assertNull(flightCrew);

        flightCrew= addAndRegisterSingleCrewMember(FlightCrewMember.Role.COPILOT, "Copilot", 5.0);
        assertNotNull(flightCrew);

        var actualQueue = crewMemberSystemUnit.crewMembersWithoutTeam();
        assertTrue(actualQueue.size() == 0);
    }

    //6, 7, 8 flight crew loomiseks errinevad tavacased


    private void testSingleCrewMember(FlightCrewMember.Role role){
        var crewMemberSystem = new HW01();
        var participant = new TestFlightCrewMember(role, "Kati Karau", 13.3);
        crewMemberSystem.registerToFlight(participant);

        var actual = crewMemberSystem.crewMembersWithoutTeam();

        assertTrue(actual.size() == 1);
        assertEquals(participant, actual.get(0));
    }

    private FlightCrew addAndRegisterSingleCrewMember(FlightCrewMember.Role role, String name, double seniority){
        var participant = new TestFlightCrewMember(role, name, seniority);
        return crewMemberSystemUnit.registerToFlight(participant);
    }

}
