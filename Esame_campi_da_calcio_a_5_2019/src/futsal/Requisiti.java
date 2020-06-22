package futsal;

public class Requisiti {
	/*<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="generator" content="pandoc" />
  <title>Campi da Calcio a 5</title>
  <style type="text/css">code{white-space: pre;}</style>
  <link rel="stylesheet" href=".css.css" type="text/css" />
</head>
<body>
<div id="header">
<h1 class="title">Campi da Calcio a 5</h1>
</div>
<!--
> To generate html and pdf use
> ```bash
> $ pandoc -s -f markdown+Smart requirements.md -c .css.css -o requirements.html
> $ pandoc -s -f markdown+Smart requirements.md -o requirements.pdf
> ```
-->
<p>Sviluppare una sistema che assista nella gestione delle prenotazioni, di un impianto sportivo di campi da calcio a 5 (futsal).</p>
<p>Tutte le classi devono essere nel package <code>it.polito.oop.futsal</code>. La classe <em>facade</em> attraverso cui tutte le operazioni sono eseguite � <code>Fields</code>.</p>
<p>La classe <code>TestApp</code> nel default package contiene una sintetico test per l�applicazione..</p>
<p>La documentazione JDK � accessibile all�URL <a href="https://oop.polito.it/api/index.html" class="uri">https://oop.polito.it/api/index.html</a>.</p>


<h2 id="r1-campi">R1: Campi</h2>
<p>L�impianto � composto da una serie di campi con diverse caratteristiche che possono essere inizializzati tramite il metodo <code>defineFields(Features...)</code> che accetta una serie di descrittori di campi.</p>
<p>Il descrittore � un oggetto di classe <code>Features</code> che include tre attributi booleani:</p>
<ul>
<li><code>indoor</code>: il campo � coperto;</li>
<li><code>heating</code>: il campo ha un impianto di riscaldamento, per default non c��;</li>
<li><code>ac</code>: il campo ha un impianto di aria condizionata, per default non � presente;</li>
</ul>
<p>Riscaldamento e aria condizionata possono essere presenti solo per un campo coperto. Se questa condizione non � verificata viene lanciata un�eccezione di <code>FutsalException</code>.</p>
<p>Ai campi viene assegnata una numerazione che parte da 1 secondo l�ordine con cui sono stati definiti nella chiamata del metodo.</p>
<p>� possibile conoscere il numero totale di campi con il metodo <code>countFields()</code> ed il numero totale di campi indoor con il metodo <code>countIndoor()</code>.</p>
<p>L�impianto sportivo ha un orario di apertura ed uno di chiusura che sono accessibili tramite i metodi getter e setter: <code>getOpeningTime()</code>, <code>setOpeningTime()</code>, <code>getClosingTime()</code>, <code>setClosingTime()</code>. Gli orari sono rappresentati da stringhe col formato <code>&quot;hh:mm&quot;</code>, dove <code>hh</code> rappresenta le ore e <code>mm</code> i minuti. Si assuma che l�orario di chiusura sia entro la mezzanotte.</p>


<h2 id="r2-soci">R2: Soci</h2>
<p>I soci dell�impianto vengono registrati tramite il metodo <code>newAssociate(String, String, String)</code> che riceve come parametri nome, cognome e numero di telefono. Il metodo restituisce un codice univoco (<code>int</code>).</p>
<p>� possibile conoscere, dato il codice univoco, nome, cognome e telefono tramite i metodi <code>getFirst()</code>, <code>getLast()</code> e <code>getPhone()</code>. A fronte di un codice inesistente il metodo lancia un�eccezione di <code>FutsalException</code>.</p>
<p>Inoltre il metodo <code>countAssociates()</code> restituisce il numero di soci registrati.</p>


<h2 id="r3-prenotazione-campi.">R3: Prenotazione Campi.</h2>
<p>I vari campi possono essere prenotati per blocchi di 1 ora, a partire dall�ora di aperture fino all�ora di chiusura.</p>
<p>La prenotazione di un campo avviene tramite il metodo <code>bookField()</code> che riceve come parametri il numero del campo, il codice univoco del campo e l�ora di inizio.</p>
<p>L�ora di inizio della prenotazione deve essere allineata all�orario di apertura (ovvero la differenza in minuti tra la prenotazione e l�apertura deve essere un multiplo di 60).</p>
<blockquote>
<p>Ad esempio se l�orario di aperture sono le <code>&quot;14:30&quot;</code>, � possibile prenotare solo alle mezze ore, ovvero <code>&quot;18:30&quot;</code> � valido come orario mentre <code>&quot;20:15&quot;</code> non lo �.</p>
</blockquote>
<p>Se il numero del campo, il codice del socio o l�orario non sono validi viene lanciata un�eccezione di <code>FutsalException</code>.</p>
<p>Dato un campo ed un orario � possibile sapere se � prenotato tramite il metodo <code>isBooked()</code> che riceve come parametri il numero del campo e l�orario e restituisce un valore booleano.</p>


<h2 id="r4-disponibilit�-e-occupazione-campi">R4: Disponibilit� e occupazione campi</h2>
<p>� possibile sapere qual�� il livello di occupazione di un campo (ovvero il numero di prenotazioni) tramite il metodo <code>getOccupation()</code> che accetta il numero di un campo e restituisce il numero di prenotazioni fatte.</p>
<p>Prima di effettuare una prenotazione � possibile verificare la disponibilit� dei campi che possiedono certe caratteristiche.</p>
<p>Il metodo <code>findOptions()</code> accetta come parametri un orario ed un oggetto <code>Features</code> e restituisce una serie di opzioni corrispondenti ai campi che hanno le caratteristiche richieste e sono liberi all�ora specificata. La lista � ordinata per occupazione decrescente e poi per numero di campo crescente.</p>
<p>Il metodo restituisce una lista di alternative rappresentate da oggetti che implementano l�interfaccia <code>FieldOption</code>.</p>
<p>L�interfaccia offre i metodi getter <code>getField()</code>, e <code>getOccupation()</code> che restituiscono il numero del campo e l�occupazione.</p>


<h2 id="r5-statistiche">R5: Statistiche</h2>
<p>Sono definiti i seguenti metodi per raccogliere statistiche:</p>
<ul>
<li><p><code>countServedAssociates()</code> restituisce il numero totale di associati che hanno effettuato almeno una prenotazione.</p></li>
<li><p><code>occupation()</code> restituisce il livello di occupazione dell�impianto in termini di percentuale. Viene calcolato 
come il rapporto tra il numero di prenotazioni in tutti i campi e il numero di blocchi da un�ora disponibili tra l�orario di apertura 
e quello di chiusura.</p></li>
<li><p><code>fieldTurnover()</code> restituisce una mappa che riporta come chiavi i numeri dei campi e come valori il numero di 
prenotazioni presso tali campi.</p></li>
</ul>
</body>
</html>*/
}