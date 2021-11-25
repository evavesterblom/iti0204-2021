package ee.ttu.algoritmid.flights;

import java.math.BigDecimal;
import java.util.*;

public class HW01 implements FlightCrewRegistrationSystem {

    //FlightCrewMemberQueueMap flightCrewMemberQueue = new FlightCrewMemberQueueMap();
    FlightCrewMemberQueueBst flightCrewMemberQueueBst = new FlightCrewMemberQueueBst();

    boolean flightAttendantInclusiveFrom = true;
    boolean flightAttendantInclusiveTo = true; //3Y
    boolean pilotInclusiveFrom = true;
    boolean pilotInclusiveTo = true;

    double pilotMinSeniorityOffset = 5.0;
    double pilotMaxSeniorityOffset = 10.0;
    double coPilotSeniorityOffset = 3.0;

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
        return flightCrewMemberQueueBst.getAllMembersFromQueueSorted();
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

        if (Double.compare(coPilotSeniority, coPilotSeniorityOffset) < 0) {
            flightCrewMemberQueueBst.addToQueue(coPilot);
            return null;
        }

        var matchedFlightAttendants = flightCrewMemberQueueBst.getFromQueueByRange(
                FlightCrewMember.Role.FLIGHT_ATTENDANT,
                0.0,
                Double.max(0.0, coPilotSeniority - coPilotSeniorityOffset),
                flightAttendantInclusiveFrom,
                flightAttendantInclusiveTo,
                true);

        var matchedPilots = flightCrewMemberQueueBst.getFromQueueByRange(
                FlightCrewMember.Role.PILOT,
                coPilotSeniority + pilotMinSeniorityOffset,
                coPilotSeniority + pilotMaxSeniorityOffset,
                pilotInclusiveFrom,
                pilotInclusiveTo,
                false);

        if(matchedFlightAttendants.size() > 0 && matchedPilots.size() > 0) {

            var flightAttendant = matchedFlightAttendants.get(0);
            var pilot = matchedPilots.get(0);
            var flightCrew = new FlightCrewImpl(pilot, coPilot, flightAttendant);

            flightCrewMemberQueueBst.removeFromQueue(flightAttendant);
            flightCrewMemberQueueBst.removeFromQueue(pilot);

            return flightCrew;
        }

        flightCrewMemberQueueBst.addToQueue(coPilot);
        return null;
    }

    private FlightCrew handleNewPilot(FlightCrewMember pilot) {

        var pilotSeniority = pilot.getWorkExperience();

        if (Double.compare(pilotSeniority, /*pilotMinSeniorityOffset*/8) < 0) {
            flightCrewMemberQueueBst.addToQueue(pilot);
            return null;
        }

        var matchedCopilots = flightCrewMemberQueueBst.getFromQueueByRange(
                FlightCrewMember.Role.COPILOT,
                Double.max(0.0, pilotSeniority - pilotMaxSeniorityOffset),
                Double.max(0.0, pilotSeniority - pilotMinSeniorityOffset),
                pilotInclusiveFrom,
                pilotInclusiveTo,
                true);


        for (var coPilot : matchedCopilots) {

            var coPilotSeniority = coPilot.getWorkExperience();

            if (Double.compare(coPilotSeniority, coPilotSeniorityOffset) < 0){
                continue;
            }

            var matchedFlightAttendants = flightCrewMemberQueueBst.getFromQueueByRange(
                    FlightCrewMember.Role.FLIGHT_ATTENDANT,
                    0.0,
                    Double.max(0.0, coPilotSeniority - coPilotSeniorityOffset),
                    flightAttendantInclusiveFrom,
                    flightAttendantInclusiveTo,
                    true);

            if (matchedFlightAttendants.size() > 0) {
                var flightAttendant = matchedFlightAttendants.get(0);
                var flightCrew = new FlightCrewImpl(pilot, coPilot, flightAttendant);

                flightCrewMemberQueueBst.removeFromQueue(flightAttendant);
                flightCrewMemberQueueBst.removeFromQueue(coPilot);

                return flightCrew;
            }
        }

        flightCrewMemberQueueBst.addToQueue(pilot);
        return null;
    }

    private FlightCrew handleNewFlightAttendant(FlightCrewMember flightAttendant) {

        var seniority = flightAttendant.getWorkExperience();

        var matchedCopilots = flightCrewMemberQueueBst.getFromQueueByRange(
                FlightCrewMember.Role.COPILOT,
                seniority + coPilotSeniorityOffset,
                Double.MAX_VALUE,
                flightAttendantInclusiveFrom,
                flightAttendantInclusiveTo,
                false);

        for (var coPilot : matchedCopilots) {

            var coPilotSeniority = coPilot.getWorkExperience();

            var matchedPilots = flightCrewMemberQueueBst.getFromQueueByRange(
                    FlightCrewMember.Role.PILOT,
                    coPilotSeniority + pilotMinSeniorityOffset,
                    coPilotSeniority + pilotMaxSeniorityOffset,
                    pilotInclusiveFrom,
                    pilotInclusiveTo,
                    false);

            if (matchedPilots.size() > 0) {
                var pilot = matchedPilots.get(0);
                var flightCrew = new FlightCrewImpl(pilot, coPilot, flightAttendant);

                flightCrewMemberQueueBst.removeFromQueue(pilot);
                flightCrewMemberQueueBst.removeFromQueue(coPilot);

                return flightCrew;
            }
        }

        flightCrewMemberQueueBst.addToQueue(flightAttendant);
        return null;
    }
}

