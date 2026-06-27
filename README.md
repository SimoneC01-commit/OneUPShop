## 🛠️ Configurazione del Database Locale

Per gestire il problema delle credenziali di MySql nell'avvio di connessioni al database è necessario creare un file db.properties.

### 1. Creazione del file delle proprietà
In Eclipse, vai nella cartella sorgente del progetto al percorso:
`src/main/java/`

Crea un nuovo file chiamato esattamente **`db.properties`**.

### 2. Configurazione dei parametri
Incolla all'interno del file appena creato le tue credenziali locali di MySQL, seguendo questa struttura:

```properties
db.url=jdbc:mysql://localhost:3306/oneupshop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimecode=false&serverTimezone=UTC
db.username=root
db.password=INSERISCI_QUI_LA_TUA_PASSWORD
