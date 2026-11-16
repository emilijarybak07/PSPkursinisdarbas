Alternative Fashion Finder (Stage II Prototype)

Spring Boot 3 prototipas
🛠️ Reikalingi įrankiai ir versijos

Norint sėkmingai sukompiliuoti, paleisti ir testuoti projektą, kompiuterio aplinkoje turi būti paruošti šie įrankiai:

Java JDK: 21 (Prototipas sukurtas ir ištestuotas naudojant Azul Zulu JDK 21).
Apache Maven: 3.8+ (arba integruotas Maven variklis per IDE / Maven Wrapper mvnw).
IDE (Rekomenduojama): IntelliJ IDEA (2023.3 ar naujesnė versija).

⚙️ Konfigūracija

Projektas naudoja integruotą greitąją H2 atminties duomenų bazę (in-memory database), todėl nereikia diegti jokių išorinių duomenų bazių serverių.

Visi reikalingi nustatymai jau yra paruošti faile src/main/resources/application.properties:

spring.application.name=alternative-fashion-finder
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true



🚀 Paleidimo komandos

Atlikus pradinį paruošimą (parsisiuntus projektą), prototipas pasileidžia viena dokumentuota komanda:
1 būdas: Naudojant sisteminį Maven (terminale projekto šakniniame aplanke)
mvn spring-boot:run

2 būdas: Naudojant Maven Wrapper (jei projekte sugeneruoti mvnw failai)
Linux / macOS:
./mvnw spring-boot:run

Windows (Command Prompt / PowerShell):
mvnw.cmd spring-boot:run

(Pastaba: Naudojant IntelliJ IDEA, projektą taip pat galima paleisti vienu paspaudimu – atidarius pagrindinę klasę su main metodu ir paspaudus žalią Run mygtuką).

🧪 Testų paleidimas

Norėdami paleisti visus automatizuotus vieneto (unit) ir integracinius testus, terminale įvykdykite:

Naudojant sisteminį Maven:
mvn test


Naudojant Maven Wrapper (Linux / macOS):
./mvnw test


Naudojant Maven Wrapper (Windows):
mvnw.cmd test


📊 Pavyzdiniai duomenys (DataInitializer)

Paleidus programą, DataInitializer komponentas automatiškai išvalo ir užpildo H2 duomenų bazę pradiniais testiniais duomenimis dviem skirtingiems klientams (multitenancy demonstravimui):

Klientas 1 (Client ID: 1)

Drabužių paieška:

Užklausa: "juoda odine striuke" | Biudžetas: "50-120€"
Rezultatai: Mango Leather Jacket, Zara Biker Jacket (Estetika: Biker / Edgy).

Prekės ženklų alternatyvos:
Tikslinis prekės ženklas: "our legacy" | Biudžetas: "50-200€"
Sistema automatiškai nustato estetiką: Minimalist
Siūlomos alternatyvos (išrikiuotos pagal populiarumą):
Selected Homme (Populiarumo balas: 50)
Universal Works (Populiarumo balas: 35)

Klientas 2 (Client ID: 2)
Drabužių paieška:
Užklausa: "vilnonis paltas" | Biudžetas: "150-300€"
Rezultatai: COS Wool Overcoat, Arket Classic Coat.

Prekės ženklų alternatyvos:
Tikslinis prekės ženklas: "acne studios" | Biudžetas: "100-250€"
Sistema automatiškai nustato estetiką: Scandi Minimalist
Siūlomos alternatyvos (išrikiuotos pagal populiarumą):
Carhartt WIP (Populiarumo balas: 88)
Norse Projects (Populiarumo balas: 65)

🖥️ Demonstravimo žingsniai (CLI sąsaja)

Paleiskite programą viena iš aukščiau nurodytų paleidimo komandų (pvz., mvn spring-boot:run).
Palaukite, kol Spring Boot užsikraus – terminale automatiškai pasirodys interaktyvus Konsolės Meniu (ConsoleMenuRunner).
Pasirinkite norimą operaciją įvesdami skaičių:
1 – Drabužio paieška pagal aprašymą ir kainų segmentą.
2 – Prekės ženklo alternatyvų paieška (su automatiniu estetikos pritaikymu ir rikiavimu pagal populiarumo balą).
0 – Išeiti iš programos.

Suveskite paieškos parametrus (pagal pavyzdinius duomenis):
Įveskite kliento ID: 1 (arba 2).
Įveskite ieškomą prekės ženklą: our legacy (arba acne studios 2-am klientui).
Įveskite kainų segmentą: 50-200 (arba 100-250 2-am klientui). (Sistema pati normalizuoja tarpus, didžiąsias/mažąsias raides ir valiutos ženklus).

Peržiūrėkite rezultatus: Terminalas išves atrinktas alternatyvas, jų estetiką, nuorodas bei populiarumo įvertinimą.
