# MiningSimple - Anki Notiztyp: Der High-Efficiency Japanisch-Mining Workflow

Dies ist der Notiztyp "MiningSimple", speziell entwickelt für das **Japanisch-Lernen durch Satz-Mining**. Er ist darauf ausgelegt, einen schnellen und konsistenten Workflow zu unterstützen und wurde bewusst mit **minimalem JavaScript** entworfen, um die Ladezeiten von Anki-Karten stabil schnell zu halten, im Gegensatz zu komplexeren Vorlagen (wie z.B. der bekannten JP Mining Note), die bei sehr großen Sammlungen Performance-Probleme verursachen können.

Dieser Notiztyp integriert sich nahtlos mit externen Tools wie **Yomitan** und **ShareX** zur effizienten Erfassung von Vokabeln im Kontext von ganzen Sätzen und Bildern.

---

## 🛠️ Installation und Kompatibilität

### 1. Empfohlene Installation (.apkg)

Der einfachste Weg, diesen Notiztyp zu installieren, ist der Import der bereitgestellten Deck-Datei.

1.  **Laden Sie die Datei `MiningSimple_NoteType.apkg`** aus diesem Repository herunter.
2.  **Importieren Sie die Datei** in Anki, indem Sie auf sie doppelklicken oder in Anki **Datei** → **Importieren** wählen.

Dadurch wird der Notiztyp "MiningSimple" (inklusive aller Felder, Vorlagen und Styling) automatisch in Ihrer Anki-Sammlung verfügbar gemacht.

### 2. Erforderliche Abhängigkeiten

Dieser Notiztyp ist Teil eines Systems und funktioniert nur, wenn alle folgenden Komponenten installiert und konfiguriert sind:

| Komponente | Beschreibung | AnkiWeb Code / Datei |
| :--- | :--- | :--- |
| **Felder** | Der Notiztyp muss die Felder `Word` und `Link to Related Cards` enthalten. | *Im .apkg enthalten* |
| **Link Anzeige** | Zur **Anzeige** der erzeugten Links auf der Karte. | **Hyperlink Note IDs (Code: 1423933177)** |
| **Yomitan Config** | Deine persönliche Yomitan-Konfiguration für Anki-Exporte. | **`yomitan-settings-2025-12-15-22-32-30.json`** |
| **Add-ons** | Die vollständige Liste aller benötigten Anki-Add-ons. | **`anki_addon_codes.txt`** |

---

## ⚙️ Yomitan Konfiguration

Um Yomitan korrekt für diesen Notiztyp einzurichten, gehe wie folgt vor:
1. Installiere Yomitan in deinem Browser.
2. Gehe in die Yomitan-Einstellungen und importiere die Datei **`yomitan-settings-2025-12-15-22-32-30.json`** aus diesem Repository.
3. **Vor jedem Mining-Vorgang:** Gehe in Yomitan zu **Configure Anki Flash Cards** und trage im Feld **Source** den Namen der aktuellen Episode/Quelle ein.

> **HINWEIS:** Die Namen aller verwendeten Yomitan-Wörterbücher sind in der Datei **`yomitan_dictionaries.png`** (oder ähnlich benanntes Bild) in diesem Repository abgebildet. Stellen Sie sicher, dass Sie diese Wörterbücher ebenfalls installieren.

---

## 📝 Mining-Prozess (Anime-Beispiel)

Dieser Prozess beschreibt den Mining-Workflow für Anime-Untertitel, der auf Geschwindigkeit, Kontextkontrolle und Medienintegration optimiert ist.

### A. Vorbereitung der Quellen und Tools

1.  **Medienquellen:** Anime-Streams sind online verfügbar. Untertitel (SRT-Dateien) findet man z.B. auf Kitsuneko. Für Light Novels können japanische Raws über Quellen wie z-library oder gebraucht (z.B. bei Traders in Akihabara) bezogen werden.
2.  **Textvorbereitung:**
    * **Java:** Stellen Sie sicher, dass Java installiert ist.
    * **SrtProcessor:** Dieses Repository enthält das Java-Programm **`SrtProcessor`**, das zur Präformatierung der rohen SRT-Dateien dient.
    * **Verwendung SrtProcessor:**
        a. Erstelle einen Ordner, in dem die Java-Datei liegt.
        b. Erstelle im selben Ordner eine Textdatei namens **`input.txt`** und füge die rohen SRT-Untertitel (Text im SRT-Format) ein.
        c. Kompiliere und führe das Java-Programm über die Konsole aus (CMD in der Adresszeile des Ordners eintippen).
        d. Das formatierte Skript liegt in **`output.txt`**.
3.  **Anki-Vorbereitung:**
    * Erstelle ein Deck namens **`mining`**, in dem alle neuen Karten gespeichert werden.
    * **AJT Addon:** Konfiguriere das AJT Addon so, dass automatisch Furigana für alle neuen Karten aus dem Feld `SentencePlain` in das Feld `SentenceFurigana` generiert wird.
4.  **ShareX Konfiguration:**
    * Installiere **ShareX** und konfiguriere es für die Aufnahme von Audio und Screenshots. Es muss eingerichtet werden, dass diese Medieninhalte automatisch in die entsprechenden Felder der **aktuellsten** Karte in Anki gespeichert werden (`SoundFront`, `SoundBack` und `Picture`). Hierfür sind dedizierte Online-Guides verfügbar. Die Screenshot- und Audioaufnahmefunktionen müssen über separate Tastenkürzel bedienbar sein.

### B. Mining-Schritte

1.  Öffne das formatierte Skript (`output.txt`) und füge es in einen Online-Texteditor wie `blankpage` ein.
2.  Wenn du eine Stelle zum Minen gefunden hast, markiere den gewünschten Kontext, indem du **Punkte (`.`)** am Anfang und Ende des Satzes/Kontextes einfügst.
3.  **Mining:**
    * Gehe das Skript durch und füge jedes unbekannte Wort in der markierten Stelle über Yomitan zum `mining` Deck hinzu.
    * **Achtung:** Halte den Anki Card Browser **geschlossen**, da ansonsten die Medien (Audio/Bild) nicht korrekt der zuletzt hinzugefügten Karte zugewiesen werden.
    * Nachdem das letzte Wort im Kontext hinzugefügt wurde, aktiviere die ShareX-Tastenkürzel für **Screenshot** und **Audioaufnahme** für die entsprechende Stelle. Die Medien werden nun dieser letzten Karte zugewiesen.

### C. Nachbereitung (Batch Edit)

Nachdem die gesamte Episode gemined wurde, sind Nachbearbeitungsschritte notwendig, da nur die zuletzt hinzugefügte Karte im Kontext die Medieninhalte enthält.

1.  **Medienverteilung:** Fülle die Felder `SoundFront`, `SoundBack` und `Picture` für alle **noch leeren** Karten (die nicht die letzten waren) aus demselben Kontext. Dies kann manuell, mit **BatchEdit** oder einem eigenen Add-on automatisiert werden (zukünftige Entwicklung).
2.  **Namen markieren:** Wähle alle Karten aus, deren geminetes Wort ein Eigenname war. Führe ein **Batch Edit** durch, um das Feld `isName` mit einem beliebigen Wert (z.B. einem Punkt) zu füllen.

Der Mining-Prozess ist nun abgeschlossen.

---

## 🔁 Review-Prozess

Der Review-Prozess ist darauf ausgerichtet, Kontextwissen schnell zu festigen:

1.  **Lernpriorität:** Anki sollte so eingestellt sein, dass die neuesten Karten, deren Wort am häufigsten vorkommt, zuerst gelernt werden.
2.  **Definition:** Beim Review klären Sie die exakte Unterdefinition des Wortes im Kontext und kopieren diese in die Felder **`Correct English Definition`** oder **`Correct Japanese Defintion`**.
3.  **Kontext lernen:**
    * Klicken Sie auf den **Browse**-Knopf im Review-Fenster der aktuellen Karte.
    * Sortieren Sie die Browseransicht nach **Created** (Erstellungsdatum).
    * Wählen Sie alle Karten aus, die im selben Mining-Kontext erstellt wurden.
    * Aktivieren Sie das **Link Cards Addon** (fügt die Links hinzu).
    * Wählen Sie dann alle **neuen** Karten (`New` Status) im Kontext aus, klicken Sie mit der rechten Maustaste und wählen Sie **Grade Now → Good**. Dadurch wird der gesamte Kontext sofort gelernt und in den Lernzyklus aufgenommen.

---

## 📚 Zusätzliche Felder & Nutzung

| Feldname | Zweck |
| :--- | :--- |
| **ContextBeyondSentence** | Zur Speicherung eines größeren Kontextes (Text oder umfangreicheres Audio) für tiefere Verständnis- oder Review-Sitzungen. Hierfür kann ShareX erneut verwendet werden (Beachten Sie die manuelle Korrektur der obersten Karte nach der Bearbeitung). |
| **AdditionalNotes** | Primäres Feld für Notizen, Erklärungen oder externe Verweise (z.B. AI-Erklärungen). |
| **Dump1 bis Dump10** | Dienen als temporäre Zwischenspeicher für Verarbeitungsoperationen (z.B. bei Batch-Änderungen oder Konvertierung von Notiztypen), um Datenverlust zu vermeiden. |
