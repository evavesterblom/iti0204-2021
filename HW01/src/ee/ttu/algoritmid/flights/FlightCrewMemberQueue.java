package ee.ttu.algoritmid.flights;

import java.util.*;

public class FlightCrewMemberQueue {

    private TreeMap<Double, List<FlightCrewMember>> availablePilots = new TreeMap<>();
    private TreeMap<Double, List<FlightCrewMember>> availableCopilots = new TreeMap<>();
    private TreeMap<Double, List<FlightCrewMember>> availableFlightAttendants = new TreeMap<>();
    private SortedSet<Double> seniorityCache = new TreeSet<>();

    public List<FlightCrewMember> getMembersFromQueue() {

        List<FlightCrewMember> resultList = new ArrayList<>();

        for (var seniority : seniorityCache) {

            var flightAttendants = availableFlightAttendants.get(seniority);
            if (flightAttendants != null && !flightAttendants.isEmpty()) {//addParticipantToResultList(resultList, flightAttendants);
                resultList.addAll(flightAttendants);
            }

            var coPilots = availableCopilots.get(seniority);
            if (coPilots != null && !coPilots.isEmpty()) { //addParticipantToResultList(resultList, coPilots);
                resultList.addAll(coPilots);
            }

            var pilots = availablePilots.get(seniority);
            if (pilots != null && !pilots.isEmpty()){ //addParticipantToResultList(resultList, pilots);
                var a = 1;
                resultList.addAll(pilots);
            }
        }
        return resultList;
    }

    private void addParticipantToResultList(List<FlightCrewMember> resultList, List<FlightCrewMember> participants) {
        for (var participant : participants) {
            if (participant != null) {
                resultList.add(participant);
            }
        }
    }

    //add to queue and to cache
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

    public NavigableMap<Double, List<FlightCrewMember>> getAvailableCrewMembers(FlightCrewMember.Role roleToFind, double fromSeniority, double toSeniority, boolean inclusiveSearch, boolean isReverseOrder){

            TreeMap<Double, List<FlightCrewMember>> map = new TreeMap<>();

        switch (roleToFind){
            case PILOT -> map = availablePilots;
            case FLIGHT_ATTENDANT -> map = availableFlightAttendants;
            case COPILOT -> map = availableCopilots;
        }
        var subMap = map.subMap(fromSeniority, inclusiveSearch, toSeniority, inclusiveSearch);

        if (isReverseOrder) return subMap.descendingMap();

        return subMap;
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
}


