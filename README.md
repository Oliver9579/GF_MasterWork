# Online étel rendelő applikáció
### A projekt célja
A projekt célja egy olyan étel rendelő alkalmazás fejlesztése, 
amely lehetővé teszi a felhasználók számára, hogy online rendeljenek ételt az a platformra regisztrált éttermekből.
Az alkalmazás megkönnyíti a rendelési folyamatot, átláthatóbbá teszi azt és lehetővé teszi a 
felhasználók számára, hogy kövessék rendelésük állapotát.


### Felépítés

![uml_diagram_white](https://github.com/green-fox-academy/Oliver9579-masterwork/blob/2a8afa4356eab1a445454621088b7aa64d738f04/masterwork%20databases.png)


#### Vevők:

- Az alkalmazás tárolja a rendeléseket.
- A vevők adatai is eltárolásra kerülnek, rendeléseiket lekérdezhetik REST végponton keresztül
### Funkciók
A megrendelők rendeléseket tudnak leadni REST interfészen keresztül. Lekérdezhetik rendelésük állapotát,
rendelési azonosító megadásával vagy a rendelés aktuális státuszának megadásával. Egy ügyfél törölheti is a rendelését.

A dolgozók hozzáférnek az összes megrendeléshez és megváltoztathatják azok állapotát REST interfész segítségével.

- User tud regisztrálni és bejelentkezni az alkalmazásba
- Az adott User le tudja kérni az adatait
- Le lehet kérdezni az összes vagy egy adott éttermet, ahol az étterem menüje is szerepel
- Lehet menüt törölni
- Rendelés:
  - Lehet új rendelést leadni
  - Lehet rendelést törölni
  - Le lehet kérdezni az adott User összes rendelését
  - Le lehet kérdezni a User egy adott rendelését
  - Le lehet kérdezni a User összes adott státuszú rendelését

### Az alkalmazás megvalósításához szükséges eszközök
- Java 8
- Gradle
- Spring boot
- REST API
- Spring Data JPA
- Spring Security
- Spring Dotenv
- JWT
- H2 Database
- Mockito
- Docker
- Git
- MySQL Database
- Swagger
- Flyway
- JUnit 4

### API dokumentáció
A részletes OpenAPI irányelveknek megfelelő dokumentáció a következő linken érhető el:
- [Swagger API](https://app.swaggerhub.com/apis-docs/SZABOTEMPLEOLIVER200/oliver-masterwork_api/1.0.0)


### Lehetséges további funkciók
- Email-es hitelesítés regisztráció után.
- Különböző szintű felhasználói hozzáférések és jogosultságok beállítása(User, admin, restaurant)
- User jogkörök: saját rendelésük állapotának lekérdezése, rendelés törlése
- Admin jogkörök: User jogkörök + lekérdezheti a usereket és a rendeléseiket. törölhet vagy felvehet új éttermet az adatbázisba.
- Restaurant jogkörök: a menü szerkesztése, törlése, illetve az adott rendelés státuszának megváltoztatása.
- További funkciók létrehozása a használati élmény növelése érdekében


### Alkalmazás indítása
Az oliver777/masterwork nevű Docker Hub repositoryban lévő image segítségével indítható az akalmazás. Ehhez szükségünk van egy .env.docker nevű fájlra, amelyben meg kell adnunk a saját környezeti változóinkat.

Az alkalmazás a következő parancs futtatása után a 8080-as porton elindul:
`docker run -p 8080:8080 --env-file .env.docker oliver777/masterwork`.

Ilyenkor Docker Hub-ról automatikusan elindul az alkalmazás a Docker képfájl segítségével és a 8080-as porton keresztül lehet vele kommunikálni.