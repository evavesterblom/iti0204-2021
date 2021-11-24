package ee.ttu.algoritmid.flights;

import java.util.*;

public class FlightCrewMemberQueueMap {

    private TreeMap<Double, List<FlightCrewMember>> availablePilots = new TreeMap<>();
    private TreeMap<Double, List<FlightCrewMember>> availableCopilots = new TreeMap<>();
    private TreeMap<Double, List<FlightCrewMember>> availableFlightAttendants = new TreeMap<>();
    private SortedSet<Double> seniorityCache = new TreeSet<>();

    public List<FlightCrewMember> getAllMembersFromQueueSorted() {

        List<FlightCrewMember> resultList = new ArrayList<>();

        for (var seniority : seniorityCache) {

            var flightAttendants = availableFlightAttendants.get(seniority);
            if (flightAttendants != null && !flightAttendants.isEmpty()) {
                resultList.addAll(flightAttendants);
            }

            var coPilots = availableCopilots.get(seniority);
            if (coPilots != null && !coPilots.isEmpty()) {
                resultList.addAll(coPilots);
            }

            var pilots = availablePilots.get(seniority);
            if (pilots != null && !pilots.isEmpty()){
                resultList.addAll(pilots);
            }
        }
        return resultList;
    }

    public void addToQueue(FlightCrewMember participant) {
        TreeMap<Double, List<FlightCrewMember>> queue = new TreeMap<>();

        switch (participant.getRole()){
            case COPILOT -> queue = availableCopilots;
            case PILOT ->  queue = availablePilots;
            case FLIGHT_ATTENDANT ->  queue = availableFlightAttendants;
        }

        if (!queue.containsKey(participant.getWorkExperience())) {
            queue.put(participant.getWorkExperience(), new ArrayList<FlightCrewMember>());
        }
        queue.get(participant.getWorkExperience()).add(participant);
        seniorityCache.add(participant.getWorkExperience());
    }

    public void removeFromQueue(FlightCrewMember participant){

        var seniority = participant.getWorkExperience();
        TreeMap<Double, List<FlightCrewMember>> map = new TreeMap<>();

        switch (participant.getRole()){
            case PILOT -> map = availablePilots;
            case COPILOT -> map = availableCopilots;
            case FLIGHT_ATTENDANT -> map = availableFlightAttendants;
        }

        var flightCrewMemberList = map.get(seniority);
        if(flightCrewMemberList != null){
            flightCrewMemberList.remove(participant);
        }

        if (flightCrewMemberList.isEmpty()){
            map.remove(seniority);
        }

        //TODO: remove key from seniorityCache if all roleQueues are null or empty for this seniority

    }

    public NavigableMap<Double, List<FlightCrewMember>> getFromQueueByRange(
            FlightCrewMember.Role roleToFind,
            double fromSeniority,
            double toSeniority,
            boolean fromInclusive,
            boolean toInclusive,
            boolean isReverseOrder){

        TreeMap<Double, List<FlightCrewMember>> map = new TreeMap<>();

        switch (roleToFind){
            case PILOT -> map = availablePilots;
            case FLIGHT_ATTENDANT -> map = availableFlightAttendants;
            case COPILOT -> map = availableCopilots;
        }
        var subMap = map.subMap(
                fromSeniority,
                fromInclusive,
                toSeniority,
                toInclusive);

        if (isReverseOrder) return subMap.descendingMap();

        return subMap;
    }


}


