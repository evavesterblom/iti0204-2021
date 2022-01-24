
# Ülesanne HW01
Lennufirmal on vaja enne lende moodustada lennuekipaaže. Eksisteerib 3 tüüpi liikmeid: piloot, abipiloot ja stjuuard ning neist on vaja jooksvalt meeskond moodustada kohe kui võimalik. Teie ülesandeks on ehitada efektiivselt toimiv järjekorrasüsteem, mis registreerib üksikuid piloote, abipiloote ja stjuuardeid ning moodustab neist kohe meeskonna kui sobivad meeskonnaliikmed on järjekorrasüsteemis olemas. Teie süsteem peab võimaldama lennufirmal igal hetkel töötajate ootejärjekorda vaadata.

Meeskondade tegemisel eksisteerivad järgmised piirangud: piloodi töökogemus peab olema 5-10 aastat suurem kui abipiloodil ning abipiloodi töökogemus peab olema vähemalt 3 aastat suurem kui stjuuardil. Uue töötaja lisandumisel järjekorda proovitakse temaga kohe meeskonda moodustada.

Kui uus töötaja on piloot ning temaga saaks meeskonda moodustada mitu abipilooti, siis tuleb proovida seda teha ainult selle abipiloodiga, kellega piloodil on kõige väiksem töökogemuse vahe. Samamoodi kui uus töötaja on stjuuard ning temaga saaks meeskonda moodustada mitu abipilooti, siis tuleb proovida seda teha ainult selle abipiloodiga, kellega stjuuardil on kõige väiksem töökogemuse vahe. Kui uus töötaja on abipiloot ning temaga saaks meeskonda moodustada mitu pilooti või mitu stjuuardit, siis tuleb kummagi hulgast valida väikseima töökogemuse vahega töötaja.

Kui selle protsessi järgi suudetakse meeskond moodustada, siis tuleb vastavad töötajad eemaldada järjekordadest ning kui ei õnnestu meeskonda moodustada, siis tuleb äsja saabunud töötaja lisada ootejärjekorda.

# Nõuded lahendusele
Iga osalejat iseloomustab kolmik (```String name, Role role, double workExperience```) - Vt FlightCrewMember.java. Iga väärtus on kohustuslik (sh nimi ei ole tühi String) ja töökogemus on mittenegatiivne number (≥ 0).
Meeskonna liikme registreerimiseks ja meeskonna leidmiseks kutsutakse välja registerToFlight(```FlightCrewMember participant```) meetod. Kui meeskonna liikme andmed ei vasta nõuetele, siis teda ei registreerita ja visatakse ```IllegalArgumentException```. Kui talle leitakse sobiv meeskond, siis kustutakse meeskonna liikmed ootejärjekorrast ja tagastatakse meeskonna andmed.
Kui sobivat meeskonna liiget ei ole, lisatakse uus meeskonnaliige ootejärjekorda ja tagastatakse null. Vt ```FlightCrewRegistrationSystem.java``` ```registerToFlight(FlightCrewMember participant)``` ja ```FlightCrew.java```.
NB! Järjekord peab vastu võtma ja tagastama samad ```FlightCrewMember``` objektid, mis tulevad testrist, st ```FlightCrewMember``` objektist ei tohi luua uut objekti oma implementatsiooniga.
Meeskonna liikme registreerimise ja meeskonnae leidmise keerukus ei tohi olla suurem kui O(lg n), kus n on ootejärjekorras olevate liikmete arv ja see on implementeeritud binaarse otsingupuuna.

Binaarne otsingupuu tuleb ise implementeerida primitiivsete andmetüüpide baasil.
Peab olema võimalik vaadata ootejärjekorda meetodiga ```crewMembersWithoutTeam()``` (vt ```FlightCrewRegistrationSystem.java```), mis tagastab kõik osalejad (piloodid, abipiloodid ja stjuuardid) töökogemuse kasvavas järjekorras. Kui järjekorras on erineva tüübi liikmed sama töökogemusega, siis esimesena tuleb stjuuard, järgmisena abipiloot ja viimasena piloot.
Kood on mõistlikult kommenteeritud (meetodid ja erijuhtudel mõned read). Kood, kus on iga rida kommenteeritud, vastu ei võeta ja kaitsmisele ei lubata. Kui kood on kirjutatud CleanCode' järgi (arusaadav ja loetav) siis kommenteerimise vajadus ei teki.
Otsingupuu implementeerida eraldi klassina. Otsingupuu funktsionaalsus peab olema piisavalt abstraaktne. Nt, ```findMatchingFlightAttendant(copilot)``` meetodi asemel võiks olla ```findElementLessAtLeastByK(k)```. Kõik lennumajanduse loogika peab olema eraldi.
Soovitusi:
Alguses on mõistlik ülesande enda lahenduse loogika testimiseks kasutada mõnd lihtsamat (suurema keerukusega operatsioonidega, näiteks sorteeritud ```ArrayList```) andmestruktuuri ja lisada otsingupuu, kui muu funktsionaalsus on korras.

Sisemiselt võib loogika lihtsustamiseks kasutada rohkem kui ühte andmestruktuuri (otsingupuud).
