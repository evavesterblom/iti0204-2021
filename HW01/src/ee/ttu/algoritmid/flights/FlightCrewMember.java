package ee.ttu.algoritmid.flights;

public interface FlightCrewMember {
    public enum Role {

        PILOT,
        COPILOT,
        FLIGHT_ATTENDANT

    }

    public String getName();

    public Role getRole();

    public double getWorkExperience();

}