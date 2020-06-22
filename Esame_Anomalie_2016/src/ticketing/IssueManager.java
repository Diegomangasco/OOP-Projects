package ticketing;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ticketing.Ticket.State;

import java.util.stream.Collector;

public class IssueManager {

    /**
     * Eumeration of valid user classes
     */
    public static enum UserClass {
        /** user able to report an issue and create a corresponding ticket **/
        Reporter, 
        /** user that can be assigned to handle a ticket **/
        Maintainer }
    
    /**
     * Creates a new user
     * 
     * @param username name of the user
     * @param classes user classes
     * @throws TicketException if the username has already been created or if no user class has been specified
     */
    private Map<String, List<UserClass>> Utenti = new HashMap<>();
    private Map<String, Componente> Componenti = new HashMap<>();
    private Map<Integer, Ticket> Tickets = new HashMap<>();
    private Map<Integer, String> ATickets = new HashMap<>();
    public void createUser(String username, UserClass... classes) throws TicketException {
        if(this.Utenti.containsKey(username))
        	throw new TicketException();
        if(classes.length == 0)
        	throw new TicketException();
        List<UserClass> Net = new ArrayList<>();
        for(UserClass U : classes)
        	Net.add(U);
        this.Utenti.put(username, Net);
    }

    /**
     * Creates a new user
     * 
     * @param username name of the user
     * @param classes user classes
     * @throws TicketException if the username has already been created or if no user class has been specified
     */
    public void createUser(String username, Set<UserClass> classes) throws TicketException {
    	 if(this.Utenti.containsKey(username))
         	throw new TicketException();
         if(classes.isEmpty() == true)
         	throw new TicketException();
         List<UserClass> Net = new ArrayList<>();
         for(UserClass U : classes)
         	Net.add(U);
         this.Utenti.put(username, Net);
    }
   
    /**
     * Retrieves the user classes for a given user
     * 
     * @param username name of the user
     * @return the set of user classes the user belongs to
     */
    public Set<UserClass> getUserClasses(String username){
        Set<UserClass> Ret = new TreeSet<>();
        for(UserClass U : this.Utenti.get(username)) {
        	Ret.add(U);
        }
        return Ret;
    }
    
    /**
     * Creates a new component
     * 
     * @param name unique name of the new component
     * @throws TicketException if a component with the same name already exists
     */
    public void defineComponent(String name) throws TicketException {
        if(this.Componenti.containsKey(name))
        	throw new TicketException();
        this.Componenti.put(name, new Componente(name));
    }
    
    /**
     * Creates a new sub-component as a child of an existing parent component
     * 
     * @param name unique name of the new component
     * @param parentPath path of the parent component
     * @throws TicketException if the the parent component does not exist or 
     *                          if a sub-component of the same parent exists with the same name
     */
    public void defineSubComponent(String name, String parentPath) throws TicketException {
        String [] O = parentPath.split("/");
        Componente C = this.Componenti.get(O[1]);
        for(int i = 2; i<O.length; i++) {
        	if(C.hasSub(O[i])) {
        		C = C.getSub(O[i]);
        	}
        	else throw new TicketException();
        }
        if(C.hasSub(name)) throw new TicketException();
        C.addSub(name);
        
    }
   
   
    /**
     * Retrieves the sub-components of an existing component
     * 
     * @param path the path of the parent
     * @return set of children sub-components
     */
    public Set<String> getSubComponents(String path){
        String [] OG = path.split("/");
        Componente C = this.Componenti.get(OG[1]);
        if(!this.Componenti.containsKey(OG[1])) return null;
        for(int i = 2; i<OG.length; i++) {
        	if(C.hasSub(OG[i])) {
        		C = C.getSub(OG[i]);
        	}
        	else return null;
        }
        return C.getSub().keySet();
    }

    /**
     * Retrieves the parent component
     * 
     * @param path the path of the parent
     * @return name of the parent
     */
    public String getParentComponent(String path){
        String [] O = path.split("/");
        if(O.length==2)
        	return null;
        String G = "";
        for(int i = 1; i<O.length-1; i++) {
        	G +=  "/" + O[i] ;
        }
        return G;
    }

    /**
     * Opens a new ticket to report an issue/malfunction
     * 
     * @param username name of the reporting user
     * @param componentPath path of the component or sub-component
     * @param description description of the malfunction
     * @param severity severity level
     * 
     * @return unique id of the new ticket
     * 
     * @throws TicketException if the user name is not valid, the path does not correspond to a defined component, 
     *                          or the user does not belong to the Reporter {@link IssueManager.UserClass}.
     */
    private static int num = 0;
    public int openTicket(String username, String componentPath, String description, Ticket.Severity severity) throws TicketException {
        if(!this.Utenti.containsKey(username))
        	throw new TicketException();
        if(!this.Utenti.get(username).contains(UserClass.Reporter))
        	throw new TicketException();
        String [] OG = componentPath.split("/");
        if(!this.Componenti.containsKey(OG[1])) throw new TicketException();
        Componente C = this.Componenti.get(OG[1]);
        for(int i = 2; i<OG.length-1; i++) {
        	if(C.hasSub(OG[i]))
        		C = C.getSub(OG[i]);
        	else
        		throw new TicketException();
        }
        num++;
        this.Tickets.put(num, new Ticket(num, username, componentPath, description, severity));
        return num;
    }
    
    /**
     * Returns a ticket object given its id
     * 
     * @param ticketId id of the tickets
     * @return the corresponding ticket object
     */
    public Ticket getTicket(int ticketId){
        if(this.Tickets.containsKey(ticketId))
        	return this.Tickets.get(ticketId);
        return null;
    }
    
    /**
     * Returns all the existing tickets sorted by severity
     * 
     * @return list of ticket objects
     */
    public List<Ticket> getAllTickets(){
        return this.Tickets.values().stream().sorted((T1, T2) -> T1.getSeverity().compareTo(T2.getSeverity())).collect(Collectors.toList());
    }
    
    /**
     * Assign a maintainer to an open ticket
     * 
     * @param ticketId  id of the ticket
     * @param username  name of the maintainer
     * @throws TicketException if the ticket is in state <i>Closed</i>, the ticket id or the username
     *                          are not valid, or the user does not belong to the <i>Maintainer</i> user class
     */
    public void assingTicket(int ticketId, String username) throws TicketException {
        if(!this.Utenti.containsKey(username))
        	throw new TicketException();
        if(!this.Utenti.get(username).contains(UserClass.Maintainer))
        	throw new TicketException();
        if(!this.Tickets.containsKey(ticketId))
        	throw new TicketException();
        this.Tickets.get(ticketId).setStateA(username);
        this.ATickets.put(ticketId, username);
    }

    /**
     * Closes a ticket
     * 
     * @param ticketId id of the ticket
     * @param description description of how the issue was handled and solved
     * @throws TicketException if the ticket is not in state <i>Assigned</i>
     */
    public void closeTicket(int ticketId, String description) throws TicketException {
        if(!this.ATickets.containsKey(ticketId))
        	throw new TicketException();
        this.Tickets.get(ticketId).setSolution(description);
        this.Tickets.get(ticketId).setStateC();
    }

    /**
     * returns a sorted map (keys sorted in natural order) with the number of  
     * tickets per Severity, considering only the tickets with the specific state.
     *  
     * @param state state of the tickets to be counted, all tickets are counted if <i>null</i>
     * @return a map with the severity and the corresponding count 
     */
    public SortedMap<Ticket.Severity,Long> countBySeverityOfState(Ticket.State state){
        if(state!=null)
        	return this.Tickets.values().stream()
        			.filter(T -> T.getState().compareTo(state)==0)
        			.collect(Collectors.groupingBy(T -> T.getSeverity(),
        					TreeMap::new,
        					Collectors.counting()));
        else
        	return this.Tickets.values().stream()
        			.collect(Collectors.groupingBy(T -> T.getSeverity(),
        					TreeMap::new,
        					Collectors.counting()));
    }

    /**
     * Find the top maintainers in terms of closed tickets.
     * 
     * The elements are strings formatted as <code>"username:###"</code> where <code>username</code> 
     * is the user name and <code>###</code> is the number of closed tickets. 
     * The list is sorter by descending number of closed tickets and then by username.

     * @return A list of strings with the top maintainers.
     */
    private String Formattatore2(Entry<String, Integer> E) {
    	return E.getKey() + String.format("%3d", E.getValue());
    }
    private List<String> Formattatore(Map<String, Integer> M){
    	return M.entrySet().stream()
    			.sorted((E1, E2) -> {
    				if(E1.getValue() == E2.getValue())
    					return E1.getKey().compareTo(E2.getKey());
    				return E2.getValue() - E1.getValue();
    			})
    			.map(E -> this.Formattatore2(E)).collect(Collectors.toList());
    }
    public List<String> topMaintainers(){
        return Formattatore(this.Utenti.entrySet().stream()
        		.filter(E -> E.getValue().contains(UserClass.Maintainer))
        		.collect(Collectors.groupingBy((Entry<String, List<UserClass>> E) -> E.getKey(),
        				HashMap::new,
        				Collector.of(ArrayList<Ticket>::new, 
        						(List<Ticket> a, Entry<String, List<UserClass>> E) -> {
        							for(Ticket T : this.Tickets.values()) {
        								if(T.isClosed()==true && T.getRisolutore().compareTo(E.getKey())==0) {
        									a.add(T);
        								}
        							}
        						}, 
        						(List<Ticket> a, List<Ticket> b) -> {a.addAll(b); return a;}, 
        						(List<Ticket> a) -> {return a.size();}))));
    }

}
