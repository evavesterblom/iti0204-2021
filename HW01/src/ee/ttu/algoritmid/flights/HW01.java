package ee.ttu.algoritmid.flights;

import java.util.*;

public class HW01 implements FlightCrewRegistrationSystem {

    FlightCrewMemberQueue flightCrewMemberQueue = new FlightCrewMemberQueue();


    @Override //todo
    public FlightCrew registerToFlight(FlightCrewMember participant) throws IllegalArgumentException {

        checkParticipantArguments(participant);

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

    @Override //todo
    public List<FlightCrewMember> crewMembersWithoutTeam() {
        return flightCrewMemberQueue.getMembersFromQueue();
    }

    private void checkParticipantArguments(FlightCrewMember participant) throws IllegalArgumentException{
        if (participant == null) throw new IllegalArgumentException("Object not valid!");
        if (participant.getName().isEmpty() || participant.getName().isBlank()) throw new IllegalArgumentException("Name not valid!");
        if (!(participant.getRole().equals(FlightCrewMember.Role.COPILOT) ||
                participant.getRole().equals(FlightCrewMember.Role.PILOT) ||
                participant.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT))){
            throw new IllegalArgumentException("Role not valid!");
        }
        if (participant.getWorkExperience() <= 0) throw new IllegalArgumentException("Seniority not valid!");
    }

    private FlightCrew handleNewCopilot(FlightCrewMember participant) {

        var seniority = participant.getWorkExperience();
        var matchedFlightAttendants = flightCrewMemberQueue.getAvailableCrewMembers(FlightCrewMember.Role.FLIGHT_ATTENDANT, 0.0, Math.max(0.0, seniority-3.0), true);
        var matchedPilots = flightCrewMemberQueue.getAvailableCrewMembers(FlightCrewMember.Role.PILOT, seniority+5, seniority+10, true);

        if(matchedFlightAttendants.size() > 0 && matchedPilots.size() > 0)
        {
            var flightAttendant = matchedFlightAttendants.firstEntry().getValue().get(0);
            var pilot = matchedPilots.firstEntry().getValue().get(0);
            var flightCrew = new FlightCrewImpl(pilot, participant, flightAttendant);
            flightCrewMemberQueue.removeFromQueue(participant);

            return flightCrew;
        }
        else flightCrewMemberQueue.addToQueue(participant);

        return null;
    }

    private FlightCrew handleNewPilot(FlightCrewMember participant) {
        flightCrewMemberQueue.addToQueue(participant);
        return null;
    }

    private FlightCrew handleNewFlightAttendant(FlightCrewMember participant) {
        flightCrewMemberQueue.addToQueue(participant);
        return null;
    }

}

