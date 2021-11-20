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
        if (participant.getName() == null) throw new IllegalArgumentException("Name null!");
        if (participant.getRole() == null) throw new IllegalArgumentException("Role null!");

        if (participant.getName().isEmpty() || participant.getName().isBlank()) throw new IllegalArgumentException("Name not valid!");

        if (!(participant.getRole().equals(FlightCrewMember.Role.COPILOT) ||
                participant.getRole().equals(FlightCrewMember.Role.PILOT) ||
                participant.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT))){
            throw new IllegalArgumentException("Role not valid!");
        }

        if (participant.getWorkExperience() < 0) throw new IllegalArgumentException("Seniority not valid!");
    }

    private FlightCrew handleNewCopilot(FlightCrewMember participant) {

        var coPilotSeniority = participant.getWorkExperience();
        var matchedFlightAttendants = flightCrewMemberQueue.getAvailableCrewMembers(
                FlightCrewMember.Role.FLIGHT_ATTENDANT,
                0.0,
                Math.max(0.0, coPilotSeniority-3.0),
                true,
                true);
        var matchedPilots = flightCrewMemberQueue.getAvailableCrewMembers(FlightCrewMember.Role.PILOT, coPilotSeniority+5, coPilotSeniority+10, true, false);

        if(matchedFlightAttendants.size() > 0
                && matchedPilots.size() > 0) {

            var flightAttendant = matchedFlightAttendants.firstEntry().getValue().get(0);
            var pilot = matchedPilots.firstEntry().getValue().get(0);

            var flightCrew = new FlightCrewImpl(pilot, participant, flightAttendant);

            flightCrewMemberQueue.removeFromQueue(flightAttendant);
            flightCrewMemberQueue.removeFromQueue(pilot);

            return flightCrew;

        }
        else flightCrewMemberQueue.addToQueue(participant);

        return null;
    }

    private FlightCrew handleNewPilot(FlightCrewMember participant) {

        var seniority = participant.getWorkExperience();
        boolean tooInExperienced = (seniority - 5.0) < 0.0;

        if (tooInExperienced) {
            flightCrewMemberQueue.addToQueue(participant);
            return null;
        }

        var matchedCopilots = flightCrewMemberQueue.getAvailableCrewMembers(
                FlightCrewMember.Role.COPILOT,
                Math.max(0.0, seniority - 10.0),
                Math.max(0.0, seniority - 5.0),
                true,
                true);

        for (var coPilots : matchedCopilots.values()) {
            for (var coPilot : coPilots) {
                var coPilotSeniority = coPilot.getWorkExperience();
                var matchedFlightAttendants = flightCrewMemberQueue.getAvailableCrewMembers(
                        FlightCrewMember.Role.FLIGHT_ATTENDANT,
                        0.0,
                        Math.max(0.0, coPilotSeniority - 3.0),
                        true,
                        true);
                if (matchedFlightAttendants.size() > 0) {
                    var flightAttendant = matchedFlightAttendants.firstEntry().getValue().get(0);
                    var flightCrew = new FlightCrewImpl(participant, coPilot, flightAttendant);

                    flightCrewMemberQueue.removeFromQueue(flightAttendant);
                    flightCrewMemberQueue.removeFromQueue(coPilot);

                    return flightCrew;
                }
            }
        }

        //add to queue
        flightCrewMemberQueue.addToQueue(participant);
        return null;
    }

    private FlightCrew handleNewFlightAttendant(FlightCrewMember participant) {

        var seniority = participant.getWorkExperience();

        var matchedCopilots = flightCrewMemberQueue.getAvailableCrewMembers(
                FlightCrewMember.Role.COPILOT,
                seniority + 3.0,
                999.9,
                true,
                false);

        for (var coPilots : matchedCopilots.values()) {
            for (var coPilot : coPilots) {
                var coPilotSeniority = coPilot.getWorkExperience();
                var matchedPilots = flightCrewMemberQueue.getAvailableCrewMembers(
                        FlightCrewMember.Role.PILOT,
                        coPilotSeniority + 5,
                        coPilotSeniority + 10,
                        true,
                        false);

                if (matchedPilots.size() > 0) {
                    var pilot = matchedPilots.firstEntry().getValue().get(0);
                    var flightCrew = new FlightCrewImpl(pilot, coPilot, participant);

                    flightCrewMemberQueue.removeFromQueue(pilot);
                    flightCrewMemberQueue.removeFromQueue(coPilot);

                    return flightCrew;
                }
            }
        }

        flightCrewMemberQueue.addToQueue(participant);
        return null;
    }

}

