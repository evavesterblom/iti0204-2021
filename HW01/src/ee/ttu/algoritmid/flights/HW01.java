package ee.ttu.algoritmid.flights;

import java.util.List;
import java.util.TreeMap;

public class HW01 implements FlightCrewRegistrationSystem {

    //Self maintaining trees
    private TreeMap<Double, FlightCrewMember> availablePilots;
    private TreeMap<Double, FlightCrewMember> availableCopilots;
    private TreeMap<Double, FlightCrewMember> availableFlightAttendants;

    @Override
    public FlightCrew registerToFlight(FlightCrewMember participant) throws IllegalArgumentException {
        // TODO
        return null;
    }

    @Override
    public List<FlightCrewMember> crewMembersWithoutTeam() {
        // TODO
        return null;
    }
}