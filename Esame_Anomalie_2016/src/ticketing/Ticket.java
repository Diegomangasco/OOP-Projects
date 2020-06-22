package ticketing;

/**
 * Class representing the ticket linked to an issue or malfunction.
 * 
 * The ticket is characterized by a severity and a state.
 */
public class Ticket {
    
    /**
     * Enumeration of possible severity levels for the tickets.
     * 
     * Note: the natural order corresponds to the order of declaration
     */
    public enum Severity { Blocking, Critical, Major, Minor, Cosmetic };
    
    /**
     * Enumeration of the possible valid states for a ticket
     */
    public static enum State { Open, Assigned, Closed }
    private int code;
    private String userName;
    private String componentPath;
    private String description;
    private Ticket.Severity severity;
    private String solution;
    private State state = State.Open;
    private String Risolutore;
    public Ticket(int code, String userName, String componentPath, String description, Ticket.Severity severity) {
    	this.code = code;
    	this.description = description;
    	this.userName = userName;
    	this.componentPath = componentPath;
    	this.severity = severity;
    }
    public int getId(){
        return this.code;
    }

    public String getDescription(){
        return this.description;
    }
    
    public Severity getSeverity() {
        return this.severity;
    }

    public String getAuthor(){
        return this.userName;
    }
    public String getRisolutore() {
    	return this.Risolutore;
    }
    public String getComponent(){
        return this.componentPath;
    }
    public boolean isClosed() {
    	if(this.state == State.Closed)
    		return true;
    	else
    		return false;
    }
    
    public State getState(){
        return this.state;
    }
    public void setStateA(String Risolutore) {
    	this.state = State.Assigned;
    	this.Risolutore = Risolutore;
    }
    public void setStateC() {
    	this.state = State.Closed;
    }
    public void setSolution(String solution) {
    	this.solution = solution;
    }
    
    public String getSolutionDescription() throws TicketException {
        if(this.state != State.Closed)
        	throw new TicketException();
        return this.solution;
    }
}
