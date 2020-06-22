package ticketing;

public class Requisiti {
	/*

<h1>Issue Management</h1>
<p>
Si sviluppi un programma che consenta ad un'azienda di trattare le segnalazioni di anomalie relative ai componenti software che  vende.
La classe principale si chiama <b>IssueManager</b>; tutte le classi si trovano nel package <b>ticketing</b>.
    La classe <b>Example</b> presenta esempi di uso dei metodi principali.<br>
    Le eccezioni lanciate dal programma sono di tipo <b>TicketException</b>.

<p>
La <a href="https://oop.polito.it/api" target="api" target="_blank">JDK documentation</a>  si trova sul server locale.
</p>



<h2>R1: Users</h2>

Il sistema ammette due ruoli di utenti: <i>Reporter</i> and <i>Maintainer</i>. Un utente pu&ograve; svolgere un solo ruolo o entrambi.
<p>Il metodo <b>createUser()</b> riceve uno username e il set dei ruoli (<b>UserClass</b>es) che l'utente svolge.
<br> In alternativa al set si pu&ograve; usare una lista variabile di argomenti.
  <br>
    In entrambi i casi il metodo lancia un'eccezione se lo username &egrave; gi&agrave; stato inserito o se nessun ruolo &egrave; indicato.    </p>

<p>Dato uno username si pu&ograve; ottenere il set dei ruoli dell'utente corrispondente con il metodo <b>getUserClasses().</b></p>



<h2>R2: Components</h2>
    
I componenti forniti dall'azienda sono costituiti ricorsivamente da sotto-componenti. 
<p>Il metodo <b>defineComponent()</b> genera un nuovo componente dato il nome e lancia un'eccezione se esiste gi&agrave; un componente con quel nome. </p>

<p>Il metodo <b>defineSubComponent()</b> genera un nuovo sotto-componente dati il nome e il path che identifica il predecessore (componente o sotto-componente) di cui il nuovo elemento diventa sotto-componente. Lancia un'eccezione se il predecessore non esiste o se ha gi&agrave; un sotto-componente con lo stesso nome. 
<br>
    Esempio: dato il sistema in figura, per aggiungere <i>SubC</i> al componente System si scrive: <i>tm.defineSubComponent("SubC","/System");</i>, mentre per aggiungere 
    <i>SubB.2</i> si scrive: <i>tm.defineSubComponent("SubB.2","/System/SubB");</i>.
</p>

<p>Dato il path di un elemento (componente o sotto-componente) si pu&ograve; ottenere il set dei nomi semplici dei sotto-componenti con il metodo 
<b>getSubComponents()</b> e il path del predecessore con il metodo <b>getParentComponent()</b> (che d&agrave; null se l'elemento non ha un predecessore).</p>
    
<p>Si noti che i path iniziano con <i>'/'</i> e contengono la lista dei (sotto-)componenti,
    a partire dal componente di top-level, separati da <i>'/'</i>.</p>
    
<h2>R3: Ticket opening</h2>
    
<p>Un utente pu&ograve; aprire un ticket che contiene i dettagli di un'anomalia riguardante un dato elemento.</p>
    
<p>Un ticket &egrave; aperto con il metodo <b>openTicket()</b> che riceve lo username dell'utente, il path dell'elemento difettoso, la descrizione dell'anomalia e la severit&agrave; (<b>Severity</b>) della stessa.
    Il metodo d&agrave; un id univoco (intero progressivo a partire da 1) per il ticket.
    <br>
    Lancia un'eccezione se lo username non &egrave; valido, il path non identifica alcun elemento, o se l'utente non svolge il ruolo  <i>Reporter</i>.
    </p>
<p>Il metodo <b>getTicket()</b> riceve un id e d&agrave; l'oggetto <b>Ticket</b> corrispondente oppure null se l'id non &egrave; valido. Il metodo  <b>getAllTickets()</b> d&agrave; la lista dei ticket ordinata naturalmente (si legga la nota).
    
<p>La classe <i>Ticket</i> offre i metodi getter <b>getDescription()</b>, <b>getId()</b>, <b>getComponent()</b>,  <b>getAuthor()</b> e <b>getSeverity()</b>.

<p class="hint"><b>Nota</b>:
    Gli oggetti che compongono un tipo enumerativo implementano automaticamente l'interfaccia <i>Comparable</i>; l'ordinamento naturale corrisponde all'ordine con cui gli oggetti sono definiti nel tipo enumerativo. Quindi nella Severity <i>Blocking</i> precede <i>Major</i>.
</p>
    


<h2>R4: Ticket lifecycle</h2>
    
<p>I ticket hanno tre stati: <i>Open</i>, <i>Assigned</i>, <i>Closed</i>. Quando &egrave; aperto, un ticket &egrave; posto nello stato <i>Open</i>.
</p>

<p>Il metodo <b>assignTicket()</b> riceve un ticket id e uno username:  porta lo stato del ticket a <i>Assigned</i>
e collega il ticket all'utente come assegnatario del ticket. Lancia un'eccezione se il ticket id o lo username non sono validi, o se l'utente non svolge il ruolo <i>Maintainer</i>.
</p>

<p>Il metodo <b>closeTicket()</b> riceve un ticket id e la descrizione della soluzione, e porta lo stato del ticket a <i>Closed</i>. Lancia un'eccezione se il ticket non si trova nello stato <i>Assigned</i>.
</p>

<p>La classe <i>Ticket</i> offre il metodo getter <b>getState()</b>, che d&agrave; lo stato corrente del ticket. </p>

Il metodo <b>getSolutionDescription()</b> della classe <i>Ticket</i> d&agrave; la descrizione della soluzione; lancia un'eccezione se il ticket non si trova nello stato <i>Closed</i>.



<h2>R5: Statistics</h2>
    
<p>Il metodo <b>countBySeverityOfState()</b> dato uno stato dei ticket fornisce una mappa con il numero di ticket per 
Severity, considerando soltanto i ticket in quello stato oppure tutti i ticket se l'argomento &egrave; nullo. La mappa &egrave; ordinata in base alla Severity. </p>

<p>Il metodo <b>topMaintainers()</b> d&agrave; una lista di stringhe: ogni stringa ha il formato <i>"username:###"</i> dove <i>username</i> &egrave; il nome dell'utente e <i>###</i> &egrave; il numero di ticket chiusi dall'utente come maintainer. 
La lista &egrave; ordinata per numero decrescente di ticket e poi per username (in ordine alfabetico).</p>



</body>
</html>*/

}
