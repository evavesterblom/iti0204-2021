
# Homework 1 -- W01
## Task
An airline company needs to form flight crews before each flight. There are 3 types of members: pilot, co-pilot, and flight attendant, and teams need to be formed immediately as soon as suitable members are available. Your task is to build an efficient queue system that registers individual pilots, co-pilots, and flight attendants and immediately forms a team if suitable team members are present in the queue. Your system must allow the airline to view employee waiting queues at any time.

The following restrictions apply when forming teams: the pilot's work experience must be 5-10 years greater than that of the co-pilot, and the co-pilot's work experience must be at least 3 years greater than that of the flight attendant. When a new employee is added to the queue, they will immediately be considered for team formation.

If the new employee is a pilot and several co-pilots could be on their team, only the co-pilot with the smallest work experience gap will be considered. Similarly, if the new employee is a flight attendant and several co-pilots could be on their team, only the co-pilot with the smallest work experience gap to the flight attendant will be considered. If the new employee is a co-pilot and can be on the team with several pilots or several flight attendants, the employee with the smallest work experience gap from each group must be selected.

If a team can be formed using this process, the corresponding employees will be removed from the queue, and if a team cannot be formed, the newly arrived employee will be added to the waiting queue.

## Requirements for the solution
Each participant is characterized by a triplet (`String name`, `Role role`, `double workExperience`) - see FlightCrewMember.java. Each value is mandatory (including a non-empty name String) and work experience is a non-negative number (≥ 0).

To register a team member and find a team, the registerToFlight(`FlightCrewMember participant`) method is called. If the team member's data does not meet the requirements, they will not be registered and an `IllegalArgumentException` will be thrown. If a suitable team is found for them, the team members are removed from the waiting list and the team's data is returned.

If there is no suitable team member, a new team member is added to the waiting list and null is returned. See FlightCrewRegistrationSystem.java `registerToFlight(FlightCrewMember participant)` and `FlightCrew.java`.
Note! The queue must accept and return the same `FlightCrewMember` objects that come from the test, i.e., a new object with your implementation must not be created from the `FlightCrewMember` object.
The complexity of registering a team member and finding a team must not be greater than O(lg n), where n is the number of members in the waiting list, and it is implemented as a binary search tree.

The binary search tree must be implemented using primitive data types.
It must be possible to view the waiting list with the `crewMembersWithoutTeam()` method (see FlightCrewRegistrationSystem.java), which returns all participants (pilots, co-pilots, and flight attendants) in ascending order of work experience. 

If members of different types have the same work experience in the list, the order should be flight attendant, followed by co-pilot, and finally pilot.
The code is reasonably commented (methods and a few lines in special cases). 

Code where every line is commented will not be accepted and will not be allowed for defense. If the code is written according to CleanCode (understandable and readable), there is no need for comments.
Implement the binary search tree as a separate class.

The functionality of the binary search tree must be sufficiently abstract. For example, instead of the `findMatchingFlightAttendant(copilot)` method, there could be `findElementLessAtLeastByK(k)`. All aviation logic must be separate.


Recommendations:
Initially, it is advisable to use a simpler data structure (with more complex operations, such as a sorted `ArrayList`) to test the solution's own logic and add the binary search tree when other functionality is in order.
