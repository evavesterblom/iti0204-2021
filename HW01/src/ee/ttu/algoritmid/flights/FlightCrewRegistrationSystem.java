package ee.ttu.algoritmid.flights;

import java.util.List;

public interface FlightCrewRegistrationSystem {

    public FlightCrew registerToFlight(FlightCrewMember participant) throws IllegalArgumentException;

    public List<FlightCrewMember> crewMembersWithoutTeam();
}