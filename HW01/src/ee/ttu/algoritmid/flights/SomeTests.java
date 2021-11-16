package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;

import static ee.ttu.algoritmid.flights.FlightCrewMember.Role.*;

public class SomeTests {

    public static void main(String[] args) {
        testEndToEndPublic1("case 1");
        testTreeEndToEndPublic3("case2");
    }

    private static void testEndToEndPublic1(String caseId) {
        List<FlightCrewMember> requests = new ArrayList<>();
        List<Double[]> responds = new ArrayList<>();

        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 111.87271235188702));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 149.13014446637817));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 118.15884279921087));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 126.91201930928904));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 114.89255931222544));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 109.95017813853076));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 135.2804314403976));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 129.0113381082797));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 117.17445290246647));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 149.616219565876));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 142.4464464573595));
        responds.add(new Double[]{142.4464464573595, 135.2804314403976, 118.15884279921087});
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 112.96307332411018));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 120.54647103605058));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 109.62003864563893));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 107.85754701632487));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 113.16553965056355));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 109.19100446276553));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 122.39529867531402));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 149.85185340450835));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 107.3542576340957));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 135.6521925722354));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 121.35614644982459));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 137.19222718283368));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 115.2325024042173));
        responds.add(new Double[]{120.54647103605058, 115.2325024042173, 111.87271235188702});
        requests.add(new CrewMemberTemp("name", PILOT, 107.79925148438289));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 115.69514617943244));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 141.05004116276066));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 119.48406762628481));
        responds.add(new Double[]{126.91201930928904, 119.48406762628481, 112.96307332411018});
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 121.55123695269302));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 137.43465429081007));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 136.63775522384336));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 107.80738321962342));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 134.20503262809046));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 136.41362406457657));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 132.5581688243523));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 116.61559647914405));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 113.64048177408192));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 101.25023436078908));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 138.9722751016918));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 141.050396264434));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 149.20289060120234));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 135.30209476871212));
        responds.add(new Double[]{141.05004116276066, 135.30209476871212, 122.39529867531402});
        requests.add(new CrewMemberTemp("name", COPILOT, 102.85871642521627));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 102.85702116485442));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 116.69611487892308));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 149.99524000699938));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 129.0692436845838));
        responds.add(new Double[]{135.6521925722354, 129.0692436845838, 121.55123695269302});
        requests.add(new CrewMemberTemp("name", PILOT, 122.41575897386335));
        responds.add(new Double[]{122.41575897386335, 116.61559647914405, 109.62003864563893});
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 137.27549158982347));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 111.2934918372948));
        responds.add(new Double[]{117.17445290246647, 111.2934918372948, 107.80738321962342});


        testTreeEndToEnd(caseId, requests, responds);
    }

    private static void testTreeEndToEndPublic3(String caseId) {
        List<FlightCrewMember> requests = new ArrayList<>();
        List<Double[]> responds = new ArrayList<>();

        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 106.74079484660564));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 103.40420635869378));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 115.01670613016454));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 123.92405078264773));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 110.11384775145501));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 107.85279791972519));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 103.1383714694357));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 126.73359637429716));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 122.25582439186846));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 122.59641529930622));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 117.41630478217095));
        responds.add(new Double[]{123.92405078264773, 117.41630478217095, 110.11384775145501});
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 104.1548498268216));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 109.26290713765086));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 102.73232442501771));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 121.5234320660712));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 123.00500117931189));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 105.36689829786516));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 101.22128923282588));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 118.7284891034643));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 102.57770255229786));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 101.49680892459364));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 103.76732287213353));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 125.15656194885409));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 105.91768222478788));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 117.76078071530117));
        responds.add(new Double[]{117.76078071530117, 109.26290713765086, 105.91768222478788});
        requests.add(new CrewMemberTemp("name", PILOT, 107.92038463530125));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 126.95863835075266));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 124.32782639503345));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 106.94259396420519));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 107.78283619331346));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 126.54492035975412));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 116.39334316574445));
        responds.add(new Double[]{122.25582439186846, 116.39334316574445, 106.74079484660564});
        requests.add(new CrewMemberTemp("name", PILOT, 107.28468228981984));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 126.52468486884919));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 100.35650214518184));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 124.26006398215915));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 110.94376433636049));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 129.02504421229986));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 109.76864722775014));
        responds.add(new Double[]{109.76864722775014, 103.76732287213353, 100.35650214518184});
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 128.50216020038485));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 114.12857171443405));
        responds.add(new Double[]{114.12857171443405, 107.78283619331346, 104.1548498268216});
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 121.11242650193783));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 104.86512380327102));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 105.84259750343878));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 129.50920309528314));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", COPILOT, 122.93195906132785));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 126.022083570776));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 109.08702379542055));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", PILOT, 109.89368962997557));
        responds.add(null);
        requests.add(new CrewMemberTemp("name", FLIGHT_ATTENDANT, 124.09896295968753));
        responds.add(null);


        testTreeEndToEnd(caseId, requests, responds);
    }


    private static void testTreeEndToEnd(String caseId, List<FlightCrewMember> requests, List<Double[]> responds) {
        HW01 solution = new HW01();

        for (int i = 0; i < requests.size(); i++) {
            testRequestResponse(caseId, solution, requests.get(i), responds.get(i));
        }
    }


    private static void testRequestResponse(String caseId, HW01 solution, FlightCrewMember participant, Double[] expectedTeamExperience) {

        FlightCrew team = solution.registerToFlight(participant);

        if (team == null) {
            if (expectedTeamExperience != null) {
                fail("Team wasn't found, but should have: " + caseId);
            }
        } else {
            if (expectedTeamExperience == null) {
                fail("Team was found, but shouldn't have: " + caseId);
            } else {
                FlightCrewMember teamMate = team.getPilot();

                if (teamMate.getWorkExperience() != expectedTeamExperience[0]) {
                    fail("Team mate of wrong extroversion found: " + caseId);
                }

                teamMate = team.getCopilot();

                if (teamMate.getWorkExperience() != expectedTeamExperience[1]) {
                    fail("Team mate of wrong extroversion found: " + caseId);
                }

                teamMate = team.getFlightAttendant();

                if (teamMate.getWorkExperience() != expectedTeamExperience[2]) {
                    fail("Team mate of wrong extroversion found: " + caseId);
                }
            }
        }
    }

    private static void fail(String text) {
        System.err.println("ERROR: " + text);
        throw new RuntimeException(text);
    }

    private static class CrewMemberTemp implements FlightCrewMember {

        private final String name;
        private final Role role;
        private final double experience;

        public CrewMemberTemp(String name, Role role, double experience) {
            this.name = name;
            this.role = role;
            this.experience = experience;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Role getRole() {
            return role;
        }

        @Override
        public double getWorkExperience() {
            return experience;
        }

    }

}
