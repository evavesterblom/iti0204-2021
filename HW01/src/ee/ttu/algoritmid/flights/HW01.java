package ee.ttu.algoritmid.flights;

import java.util.*;

public class HW01 implements FlightCrewRegistrationSystem {

    FlightCrewMemberQueue flightCrewMemberQueue = new FlightCrewMemberQueue();

    boolean flightAttendantInclusiveFrom = true;
    boolean flightAttendantInclusiveTo = true; //3Y
    boolean pilotInclusiveFrom = true;
    boolean pilotInclusiveTo = true;

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

    private FlightCrew handleNewCopilot(FlightCrewMember coPilot) {

        var coPilotSeniority = coPilot.getWorkExperience();

        if (coPilotSeniority < 3.0) {
            flightCrewMemberQueue.addToQueue(coPilot);
            return null;
        }

        var matchedFlightAttendants = flightCrewMemberQueue.getAvailableCrewMembers(
                FlightCrewMember.Role.FLIGHT_ATTENDANT,
                0.0,
                Math.max(0.0, coPilotSeniority - 3.0),
                flightAttendantInclusiveFrom,
                flightAttendantInclusiveTo,
                true);

        var matchedPilots = flightCrewMemberQueue.getAvailableCrewMembers(
                FlightCrewMember.Role.PILOT,
                coPilotSeniority + 5.0,
                coPilotSeniority + 10.0,
                pilotInclusiveFrom,
                pilotInclusiveTo,
                false);

        if(matchedFlightAttendants.size() > 0
                && matchedPilots.size() > 0) {

            var flightAttendant = matchedFlightAttendants.firstEntry().getValue().get(0);
            var pilot = matchedPilots.firstEntry().getValue().get(0);

            var flightCrew = new FlightCrewImpl(pilot, coPilot, flightAttendant);

            flightCrewMemberQueue.removeFromQueue(flightAttendant);
            flightCrewMemberQueue.removeFromQueue(pilot);

            return flightCrew;

        }

        else flightCrewMemberQueue.addToQueue(coPilot);
        return null;
    }

    private FlightCrew handleNewPilot(FlightCrewMember pilot) {

        var pilotSeniority = pilot.getWorkExperience();

        if (pilotSeniority < 5.0) {
            flightCrewMemberQueue.addToQueue(pilot);
            return null;
        }

        var matchedCopilots = flightCrewMemberQueue.getAvailableCrewMembers(
                FlightCrewMember.Role.COPILOT,
                Math.max(0.0, pilotSeniority - 10.0),
                Math.max(0.0, pilotSeniority - 5.0),
                pilotInclusiveFrom,
                pilotInclusiveTo,
                true);

        for (var coPilots : matchedCopilots.values()) {
            for (var coPilot : coPilots) {
                var coPilotSeniority = coPilot.getWorkExperience();

                if (coPilotSeniority < 3.0){
                    continue;
                }

                var matchedFlightAttendants = flightCrewMemberQueue.getAvailableCrewMembers(
                        FlightCrewMember.Role.FLIGHT_ATTENDANT,
                        0.0,
                        Math.max(0.0, coPilotSeniority - 3.0),
                        flightAttendantInclusiveFrom,
                        flightAttendantInclusiveTo,
                        true);

                if (matchedFlightAttendants.size() > 0) {
                    var flightAttendant = matchedFlightAttendants.firstEntry().getValue().get(0);
                    var flightCrew = new FlightCrewImpl(pilot, coPilot, flightAttendant);

                    flightCrewMemberQueue.removeFromQueue(flightAttendant);
                    flightCrewMemberQueue.removeFromQueue(coPilot);

                    return flightCrew;
                }
            }
        }

        //add to queue
        flightCrewMemberQueue.addToQueue(pilot);
        return null;
    }

    private FlightCrew handleNewFlightAttendant(FlightCrewMember flightAttendant) {

        var seniority = flightAttendant.getWorkExperience();

        var matchedCopilots = flightCrewMemberQueue.getAvailableCrewMembers(
                FlightCrewMember.Role.COPILOT,
                seniority + 3.0,
                Double.MAX_VALUE,
                flightAttendantInclusiveFrom,
                flightAttendantInclusiveTo,
                false);

        for (var coPilots : matchedCopilots.values()) {
            for (var coPilot : coPilots) {

                var coPilotSeniority = coPilot.getWorkExperience();

                var matchedPilots = flightCrewMemberQueue.getAvailableCrewMembers(
                        FlightCrewMember.Role.PILOT,
                        coPilotSeniority + 5.0,
                        coPilotSeniority + 10.0,
                        pilotInclusiveFrom,
                        pilotInclusiveTo,
                        false);

                if (matchedPilots.size() > 0) {
                    var pilot = matchedPilots.firstEntry().getValue().get(0);
                    var flightCrew = new FlightCrewImpl(pilot, coPilot, flightAttendant);

                    flightCrewMemberQueue.removeFromQueue(pilot);
                    flightCrewMemberQueue.removeFromQueue(coPilot);

                    return flightCrew;
                }
            }
        }

        flightCrewMemberQueue.addToQueue(flightAttendant);
        return null;
    }

}

