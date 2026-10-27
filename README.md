 Fox and Hounds – Projekt Dokumentáció
1. A projekt célja és áttekintése
A program a klasszikus Fox and Hounds (Róka és Kutyák) nevű táblás stratégiai játék konzolos implementációja Java nyelven.

Játékszabályok röviden: Egy 8x8-as sakktáblaszerű mezőn egyetlen róka ('F') és négy kutya ('H') küzd egymással. A róka bárhová léphet átlósan egy mezőt előre vagy hátra, míg a kutyák kizárólag előre (az alsóbb sorok felé) léphetnek átlósan. A róka akkor nyer, ha sikerül áttörnie a kutyák vonalán és eljutnia a legfelső sorba (0. sor), a kutyák pedig akkor nyernek, ha teljesen körbezárják a rókát, azaz nem marad érvényes lépése.

Használt technológiák:

Java 17 (objektumorientált paradigmák)

Maven (függőségkezelés, build és csomagolás)

JUnit 5 (egységtesztelés)

2. Architektúra és Mappaszerkezet
A projekt a szabványos Maven directory structure elvet követi, ami szétválasztja a forráskódot, a teszteket és a konfigurációt:

Plaintext
Beadando/
├── pom.xml                     <-- Maven projektvezérlő és konfigurációs fájl
└── src
    ├── main
    │   └── java
    │       ├── Position.java       <-- Koordináta model osztály
    │       ├── Board.java          <-- Játéktábla és játékszabályok logikája
    │       └── FoxAndHoundsGame.java <-- Főprogram / Parancssori interakció (Main)
    └── test
        └── java
            └── BoardTest.java      <-- JUnit egységtesztek
3. Az osztályok részletes bemutatása
📍 Position.java (Koordináta Modell)
Feladata: Egy pont (mező) reprezentálása a táblán a sor (row) és oszlop (col) koordináták alapján.

Főbb metódusok:

isValid(): Ellenőrzi, hogy a koordináta a 8x8-as tábla határain belül esik-e (0 és 7 közötti értékek).

equals(): Két pozíció összehasonlítására szolgál (szükséges a figurák helyzetének ellenőrzéséhez).

 Board.java (Játéktábla és Logika)
Feladata: Ez a központi komponens tárolja a tábla állapotát egy kétdimenziós karaktertömbben (char[][] grid), nyilvántartja a róka pozícióját (foxPos), a kutyák listáját (houndPositions), valamint azt, hogy jelenleg kinek a köre van (isFoxTurn).

Főbb metódusok:

initBoard(): Alaphelyzetbe állítja a táblát (a róka a legalsó sor közepén, a kutyák a legfelső sor páratlan oszlopaiban kezdenek).

move(int fromR, int fromC, int toR, int toC): Kezeli a figurák mozgatását. Ellenőrzi a mezők érvényességét, majd a körnek megfelelően különválasztja a róka és a kutyák lépésszabályait.

isValidFoxMove() / isValidHoundMove(): Privát segédmetódusok, amelyek validálják, hogy a lépés szabályos-e (pl. átlósan 1 mező, üres célmező, kutyák esetében csak előre irányú lépés).

checkGameStatus(): Vizsgálja a győzelmi feltételeket (FOX_WIN, HOUNDS_WIN, vagy ONGOING).

 FoxAndHoundsGame.java (Parancssori Interakció - Main)
Feladata: Biztosítja a felhasználói felületet a konzolon keresztül.

Működés:

Létrehozza a Board példányt, majd egy while ciklusban (Game Loop) folyamatosan kirajzolja a táblát (printBoard()).

A Scanner osztály segítségével beolvassa a játékos lépését (honnan és hova: fromR fromC toR toC).

Kezeli az esetleges hibás bemeneteket, és kiírja a játék végén a győztest.

BoardTest.java (JUnit Egységtesztek)
Feladata: Az automatizált tesztelés biztosítása a Board osztály metódusaira.

Teszteltek:

testInitialTurnIsFox: Ellenőrzi, hogy a játék valóban a róka körével indul-e.

testValidFoxMove: Ellenőrzi, hogy a róka szabályos átlós lépése sikeresen végrehajtódik-e.

testInvalidHoundMoveBackward: Ellenőrzi, hogy a kutyák szabálytalan (hátrafele irányuló) lépése elutasításra kerül-e.

4. Maven Konfiguráció (pom.xml)
A pom.xml fájl felelős a projekt egységes kezeléséért:

Függőségek (dependencies): Letölti a JUnit 5 tesztelési keretrendszert (junit-jupiter).

Pluginok (build/plugins):

maven-compiler-plugin: Biztosítja, hogy a kód a megadott Java 17 szabvány szerint fordítódjon le.

maven-surefire-plugin: Automatikusan lefuttatja a teszteket a build folyamat során.

maven-assembly-plugin: Önálló, futtatható .jar fájlba (jar-with-dependencies) csomagolja a teljes játékot a könnyű indíthatóság érdekében.
