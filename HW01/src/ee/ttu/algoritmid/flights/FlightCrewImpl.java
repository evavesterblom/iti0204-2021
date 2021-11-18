package ee.ttu.algoritmid.flights;

public class FlightCrewImpl implements FlightCrew {

    private FlightCrewMember pilot;
    private FlightCrewMember coPilot;
    private FlightCrewMember flightAttendant;

    public FlightCrewImpl(
            FlightCrewMember pilot,
            FlightCrewMember coPilot,
            FlightCrewMember flightAttendant
    ){
        this.pilot = pilot;
        this.coPilot = coPilot;
        this.flightAttendant = flightAttendant;
    }

    @Override
    public FlightCrewMember getPilot() {
        return null;
    }

    @Override
    public FlightCrewMember getCopilot() {
        return null;
    }

    @Override
    public FlightCrewMember getFlightAttendant() {
        return null;
    }
}
