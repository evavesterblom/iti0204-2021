package ee.ttu.algoritmid.flights;

import java.util.*;

public class HW01 implements FlightCrewRegistrationSystem {

    private TreeMap<Double, List<FlightCrewMember>> availablePilots = new TreeMap<>();
    private TreeMap<Double, List<FlightCrewMember>> availableCopilots = new TreeMap<>();
    private TreeMap<Double, List<FlightCrewMember>> availableFlightAttendants = new TreeMap<>();
    private SortedSet<Double> seniorityCache = new TreeSet<>();

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

    private void checkParticipantArguments(FlightCrewMember participant) throws IllegalArgumentException{
        if (participant == null) throw new IllegalArgumentException("Object not valid!");
        if (participant.getName().isEmpty() || participant.getName().isBlank()) throw new IllegalArgumentException("Name not valid!");
        if (!participant.getRole().equals(FlightCrewMember.Role.COPILOT) ||
                !participant.getRole().equals(FlightCrewMember.Role.PILOT) ||
                !participant.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT) ){
            throw new IllegalArgumentException("Role not valid!");
        }
        if (participant.getWorkExperience() <= 0) throw new IllegalArgumentException("Seniority not valid!");
    }

    @Override //todo
    public List<FlightCrewMember> crewMembersWithoutTeam() {

        List<FlightCrewMember> resultList = new ArrayList<>();

        for (var seniority : seniorityCache) {
            var flightAttendants = availableFlightAttendants.get(seniority);
            if (flightAttendants != null) addParticipantToResultList(resultList, flightAttendants);

            var copilots = availableCopilots.get(seniority);
            if (copilots != null) addParticipantToResultList(resultList, copilots);

            var pilots = availablePilots.get(seniority);
            if (pilots != null) addParticipantToResultList(resultList, pilots);
        }

        return resultList;
    }

    private FlightCrew handleNewCopilot(FlightCrewMember participant) {

        // 1 .search for new crew and remove from queue
        // kas leidub komplekt pilooti, kas leidub abipilooti, kui jah, vali
        // 2. or add to queue
        addToQueue(participant, availableCopilots);

        return null;
    }

    private FlightCrew handleNewPilot(FlightCrewMember participant) {
        addToQueue(participant, availablePilots);
        return null;
    }

    private FlightCrew handleNewFlightAttendant(FlightCrewMember participant) {
        addToQueue(participant, availableFlightAttendants);
        return null;
    }

    private void addToQueue(FlightCrewMember participant, TreeMap<Double, List<FlightCrewMember>> queue) {
        if (!queue.containsKey(participant.getWorkExperience())) {
            queue.put(participant.getWorkExperience(), new ArrayList<FlightCrewMember>());
        }
        queue.get(participant.getWorkExperience()).add(participant);
        seniorityCache.add(participant.getWorkExperience());
    }

    private void addParticipantToResultList(List<FlightCrewMember> resultList, List<FlightCrewMember> participants) {
        for (var participant : participants) {
            if (participant != null) {
                resultList.add(participant);
            }
        }
    }

}