package ee.ttu.algoritmid.flights;

import java.util.List;
import java.util.TreeMap;

public class HW01 implements FlightCrewRegistrationSystem {

    //Self maintaining trees
    private TreeMap<Double, FlightCrewMember> availablePilots = new TreeMap<>();
    private TreeMap<Double, FlightCrewMember> availableCopilots = new TreeMap<>();;
    private TreeMap<Double, FlightCrewMember> availableFlightAttendants = new TreeMap<>();;


    @Override
    public FlightCrew registerToFlight(FlightCrewMember participant) throws IllegalArgumentException {

        switch (participant.getRole()){
            case FLIGHT_ATTENDANT:
                    return handleNewFlightAttendant(participant);
            case PILOT:
                    return handleNewPilot(participant);
            case COPILOT:
                    return handleNewCopilot(participant);
        }
        return null;
    }

    @Override
    public List<FlightCrewMember> crewMembersWithoutTeam() {
        // TODO
        return null;
    }

    private FlightCrew handleNewCopilot(FlightCrewMember participant) {
    }

    private FlightCrew handleNewPilot(FlightCrewMember participant) {
    }

    private FlightCrew handleNewFlightAttendant(FlightCrewMember participant) {
    }




    /////////
}