<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Aggiungi Prodotto</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            max-width: 400px;
            padding: 8px;
            box-sizing: border-box;
        }
        .dynamic-section {
            display: none;
            border: 1px solid #ccc;
            padding: 15px;
            margin-bottom: 15px;
            max-width: 400px;
            background-color: #f9f9f9;
        }
        .error {
            color: red;
            margin-bottom: 15px;
        }
        button {
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <h1>Aggiungi Nuovo Prodotto</h1>

    <% 
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) { 
    %>
        <div class="error"><%= errorMessage %></div>
    <% 
        } 
    %>

    <form action="<%= request.getContextPath() %>/AggiungiProdotto" method="POST" enctype="multipart/form-data">
        
        <div class="form-group">
            <label for="tipo">Tipo Prodotto:</label>
            <select name="tipo" id="tipo" onchange="gestisciTipo()" required>
                <option value="">Seleziona un tipo</option>
                <option value="Gioco">Gioco</option>
                <option value="Console">Console</option>
                <option value="Gadget">Gadget</option>
                <option value="Cabinato">Cabinato</option>
            </select>
        </div>

        <div class="form-group">
            <label for="titolo">Titolo:</label>
            <input type="text" name="titolo" id="titolo" required>
        </div>

        <div class="form-group">
            <label for="descrizione">Descrizione:</label>
            <textarea name="descrizione" id="descrizione" rows="4" required></textarea>
        </div>

        <div class="form-group">
            <label for="annoRilascio">Anno Rilascio:</label>
            <input type="number" name="annoRilascio" id="annoRilascio" required>
        </div>

        <div class="form-group">
            <label for="azienda">Azienda:</label>
            <input type="text" name="azienda" id="azienda" required>
        </div>

        <div class="form-group">
            <label for="stato">Stato:</label>
            <select name="stato" id="stato" onchange="gestisciStato()" required>
                <option value="Nuovo">Nuovo</option>
                <option value="Usato">Usato</option>
            </select>
        </div>

        <div class="form-group" id="containerNoteDifetti" style="display: none;">
            <label for="noteDifetti">Note Difetti:</label>
            <textarea name="noteDifetti" id="noteDifetti" rows="3"></textarea>
        </div>

        <div class="form-group">
            <label for="iva">IVA (%):</label>
            <input type="number" name="iva" id="iva" required>
        </div>

        <div class="form-group">
            <label for="prezzoAcquisto">Prezzo Acquisto (inserisci -1 per prezzo attuale personalizzato):</label>
            <input type="number" name="prezzoAcquisto" id="prezzoAcquisto" step="0.01" oninput="gestisciPrezzo()" required>
        </div>

        <div class="form-group" id="containerPrezzoAttuale" style="display: none;">
            <label for="prezzoAttuale">Prezzo Attuale:</label>
            <input type="number" name="prezzoAttuale" id="prezzoAttuale" step="0.01">
        </div>

        <div class="form-group">
            <label for="foto">Foto:</label>
            <input type="file" name="foto" id="foto" accept="image/*">
        </div>

        <div id="sezioneGioco" class="dynamic-section">
            <h3>Specifiche Gioco</h3>
            <div class="form-group">
                <label for="sviluppatore">Sviluppatore:</label>
                <input type="text" name="sviluppatore" id="sviluppatore">
            </div>
        </div>

        <div id="sezioneConsole" class="dynamic-section">
            <h3>Specifiche Console</h3>
            <div class="form-group">
                <label for="modelloSpecifico">Modello Specifico:</label>
                <input type="text" name="modelloSpecifico" id="modelloSpecifico">
            </div>
        </div>

        <div id="sezioneGadget" class="dynamic-section">
            <h3>Specifiche Gadget</h3>
            <div class="form-group">
                <label for="tipoMateriale">Tipo Materiale:</label>
                <input type="text" name="tipoMateriale" id="tipoMateriale">
            </div>
            <div class="form-group">
                <label for="tipoGadget">Tipo Gadget:</label>
                <input type="text" name="tipoGadget" id="tipoGadget">
            </div>
        </div>

        <div id="sezioneCabinato" class="dynamic-section">
            <h3>Specifiche Cabinato</h3>
            <div class="form-group">
                <label for="tipoSistemaArcade">Tipo Sistema Arcade:</label>
                <input type="text" name="tipoSistemaArcade" id="tipoSistemaArcade">
            </div>
            <div class="form-group">
                <label for="dimensioniCm">Dimensioni (cm):</label>
                <input type="text" name="dimensioniCm" id="dimensioniCm">
            </div>
        </div>

        <button type="submit">Salva Prodotto</button>
    </form>

    <script>
        function gestisciTipo() {
            var tipo = document.getElementById("tipo").value;
            
            document.getElementById("sezioneGioco").style.display = "none";
            document.getElementById("sezioneConsole").style.display = "none";
            document.getElementById("sezioneGadget").style.display = "none";
            document.getElementById("sezioneCabinato").style.display = "none";

            if (tipo === "Gioco") {
                document.getElementById("sezioneGioco").style.display = "block";
            } else if (tipo === "Console") {
                document.getElementById("sezioneConsole").style.display = "block";
            } else if (tipo === "Gadget") {
                document.getElementById("sezioneGadget").style.display = "block";
            } else if (tipo === "Cabinato") {
                document.getElementById("sezioneCabinato").style.display = "block";
            }
        }

        function gestisciStato() {
            var stato = document.getElementById("stato").value;
            var containerNote = document.getElementById("containerNoteDifetti");
            var inputNote = document.getElementById("noteDifetti");
            
            if (stato === "Usato") {
                containerNote.style.display = "block";
                inputNote.required = true;
            } else {
                containerNote.style.display = "none";
                inputNote.required = false;
                inputNote.value = "";
            }
        }

        function gestisciPrezzo() {
            var prezzoAcquistoVal = document.getElementById("prezzoAcquisto").value;
            var containerPrezzoAttuale = document.getElementById("containerPrezzoAttuale");
            var inputPrezzoAttuale = document.getElementById("prezzoAttuale");

            if (prezzoAcquistoVal === "-1") {
                containerPrezzoAttuale.style.display = "block";
                inputPrezzoAttuale.required = true;
            } else {
                containerPrezzoAttuale.style.display = "none";
                inputPrezzoAttuale.required = false;
                inputPrezzoAttuale.value = "";
            }
        }
    </script>
</body>
</html>