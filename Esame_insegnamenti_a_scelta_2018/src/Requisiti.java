
public class Requisiti {
	
	/*<span class="it">Gestione Insegnamenti a Scelta</span>
	

	<p class="it">Sviluppare un sistema che permetta di gestire le iscrizioni a insegnamenti a scelta.
	Gli insegnamenti hanno con un numero di posti limitati.
	Gli studenti possono scegliere diversi insegnamenti (con un ordine di priorit&agrave;).
	Il sistema effettua gli assegnamenti.
	</p> 
	

	<p class="it">Tutte le classi devono trovarsi nel package <b>it.polito.oop.elective</b>.</p>

	
	<p class="it">La classe <b>Example</b> nel package <b>main</b> contiene esempi di uso del metodi principali.</p>

	
	<p class="it">
	È possibile accedere liberamente alla <a href="https://oop.polito.it/api" target="api" target="_blank">Documentazione JDK</a>.
	</p>

	</section>

	<section class="it" lang="it">
	<h2>R1: Corsi e Studenti</h2>
	<p>
	La classe principale di facciata del sistema è <b>ElectiveManager</b>, tramite essa vengono svolte tutte le operazioni.
	</p>
	<p>Gli insegnamenti possono essere definiti
	tramite il metodo <b>addCourse(String , int )</b> il quale riceve come parametri 
	il nome (che &egrave; univoco) e il numero di posti disponibili.
	</p>
	<p>&Egrave; possibile ottenere l'elenco dei corsi definiti tramite il metodo <b>getCourses()</b>
	che restituisce l'insieme dei nomi dei corsi definiti, in ordine alfabetico.
	</p>
	<p>
	    Gli studenti possono essere registrati presso il sistema tramite chiamate al metodo
	    <b>loadStudent(String , double)</b> che riceve come parametri l'id dello studente
	    e la media dei voti. Se il metodo viene chiamato più di una volta, per lo stesso studente,
	    il risultato &egrave; quello di aggiornare la media.
	</p>
	<p>
	    Il metodo <b>getStudents()</b> restituisce l'elenco degli id degli studenti registrati.
	    Per sapere quali studenti hanno una media compresa in un dato intervallo (estremi inclusi)
	    si usa il metodo <b>getStudents(double, double)</b> che restituisce l'elenco degli id degli studenti.
	</p>
	</section>




	<section class="it">
	<h2>R2: Richieste di iscrizione</h2>


	<p>Quando uno studente vuole iscriversi agli insegnamenti a scelta pu&ograve; indicare una lista di
	insegnamenti (da 1 a 3) in ordine di priorit&agrave;.
	La richiesta viene fatta tramite il metodo <b>requestEnroll(String,List&lt;String&gt;)</b>
	che riceve come parametri l'identificatore dello studente e l'insieme degli insegnamenti desiderati 
	in ordine di priorit&agrave;, dal preferito a quello meno desiderato. Il metodo restituisce il numero
	di insegnamenti per cui è stata espressa una preferenza.
	Viene generata un'eccezione <b>ElectiveException</b> se il numero di insegnamenti espressi non è da 1 a 3,
	se l'id non corrisponde ad uno studente già inserito, oppure se gli insegnamenti non sono stati definiti.
	</p>
	<p>
	&Egrave; possibile conoscere il numero di studenti che hanno espresso una preferenza
	tramite il metodo  <b>numberRequests()</b> che restituisce una mappa che 
	associa ad ogni insegnamento il numero di studenti che l'hanno selezionato 
	come prima, seconda e terza scelta. La mappa ha come chiave il nome dell'insegnamento
	e come valore una lista di tre elementi che corrispondono alla 1<sup>a</sup>, 2<sup>a</sup> e 3<sup>a</sup> scelta. 
	La mappa deve riportare anche gli insegnamenti che non sono stati scelti da nessuno studente 
	(in tal caso la lista conterr&agrave; tre zeri).
	</p>
	</section>

	</section>

	<section class="it">
	<h2 >R3: Formazione classi</h2>

	<p>
	Quando termina il periodo per esprimere le preferenze per gli insegnamenti a scelta,
	l'ufficio offerta didattica avvia la procedura di formazione delle classi.
	La formazione avviene tramite il metodo <b>makeClasses()</b> che restituisce
	il numero di studenti non assegnati ad alcun insegnamento.
	<p>
	<p>
	Il principio seguito nell'assegnamento &egrave; che gli studenti con media pi&ugrave; elevata
	vengono soddisfatti per primi, fino ad esaurimento dei posti negli insegnamenti selezionati.<p>

	<p>Uno studente che non trova posto in nessuno degli insegnamenti viene considerato come studente non assegnato.
	</p>

	<p>&Egrave; possibile conoscere gli assegnamenti tramite il metodo 
	<b>getAssignments()</b> che restituisce una mappa che associa ad ogni
	corso l'elenco degli id degli studenti assegnati. La lista degli id
	&egrave; ordinata in base alla media dei voti decrescenti.
	</p>

	</section>


	<section class="it">
	<h2>R4: Notifica di Messaggi</h2>

	<p>
	Il sistema di gestione invia delle notifiche che riguardano la
	procedura di gestione degli insegnamenti a scelta.</p>
	<p>
	Per inviare messaggi utilizza oggetti notificatori che implementano l'interfaccia <b>Notifier</b>.
	Tali oggetti devono essere registrati tramite il metodo <i>addNotifier(Notifier)</i>.</p>
	Tali oggetti e le relative classi sono fuori dal contesto dell'esame (sono fornite da altri)
	 e possono fornire, ad esempio, notifiche via email o via sms.
	<p>
	Ogni volta che viene specificata una richiesta di iscrizione (tramite il metodo <i>requestEnroll()</i>)
	il sistema notifica la presa in carico della richiesta chiamando per tutti i notificatori registrati il metodo
	<b>requestReceived(String)</b> che riceve come parametro l'id dello studente.
	</p>
	<p>
	Dopo aver composto le classi (tramite il metodo <i>makeClasses()</i>), per ogni studente assegnato 
	ad un corso (e per ogni notificatore) viene chiamato il metodo <b>assignedToCourse(String,String)</b> 
	che riceve come parametri l'id dello studente ed il corso assegnato.

	<h3>Suggerimenti</h3>
	<ul class="hint">
	<li>L'interfaccia <i>Notifier</i> non deve essere implementata. Nel package <i>main</i>
	    viene fornita un'implementazione di esempio per poter osservare il funzionamento.
	<li>Questo requisito non richiede di implementare altri metodi oltre a 
	    <i>addNotifier(Notifier)</i>, inoltre &egrave; necessario aggiungere delle funzionalità
	    a metodi già descritti nei requisiti precedenti per inviare le notifiche.
	</ul>
	</p>

	</section>

	</section>

	<section class="it">
	<h2>R5: Statistiche</h2>

	<p>
	&Egrave; possibile sapere quale percentuale di studenti è stata 
	assegnata all'insegnamento indicato come prima (seconda o terza) scelta
	tramite il metodo: <b>successRate(int)</b> che riceve come parametro
	il numero della scelta per cui calcolare il successo, il numero varia da 1 a 3.
	Il tasso di successo viene calcolato considerando (al denominatore) tutti gli studenti, 
	anche quelli che non sono stati assegnati ad alcun insegnamento.
	</p>

	<p>L'elenco degli id degli studenti non assegnati ad alcun insegnamento
	indicati &egrave; restituito dal metodo <b>getNotAssigned()</b>.


	</section>

	</body>

	</html>*/

}
