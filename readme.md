# Setup
- Git-Repository clonen
- Git-Flow initialisieren: [Git-Flow Cheatsheet](https://danielkummer.github.io/git-flow-cheatsheet/)
  - Standart-Konfiguration verwenden (alles default)
- IDE-Projekt über Gradle erstellen
  - Windows:
    - IntelliJ: `gradlew.bat idea`
    - Eclipse: `gradlew.bat eclipse`
  - Linux / Unix:
    - IntelliJ: `./gradlew idea`
    - Eclipse: `./gradlew eclipse` 
- Erstelltes Projekt über die IDE öffnen (nicht importieren, etc.)

# Laufende Entwicklung
## Server bauen & starten
- Script dafür:
  - Windows: `start-server.bat`
  - Linux / Unix: `./start-server`
- Das Script kann über `Ctrl + C` beendet werden und stoppt dann auch den laufenden Server
  - Hinweis dazu: Unter Cygwin auf Windows wird der Server beim Beenden des Scripts nicht gestoppt. Unter Windows sollte daher die Bat-Datei verwendet werden.
- Der Server ist über [Port 8080 auf localhost](http://localhost:8080) erreichbar.
## Client bauen
- Script dafür:
  - Windows: `build-client.bat`
  - Linux / Unix: `./build-client`
- Solange das Script läuft werden Änderungen erkannt und direkt compiled. Es ist nicht nötig den Server neu zu starten, ein Refresh der Website reicht aus.
- Das Script kann über `Ctrl + C` beendet werden.