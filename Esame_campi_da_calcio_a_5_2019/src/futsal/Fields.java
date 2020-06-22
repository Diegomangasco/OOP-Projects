package futsal;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Collector;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
    
	private Map<Integer, Campo> Campi = new HashMap<>();
	private Map<Integer, Associato> Associati = new HashMap<>();
	private String oraApertura;
	private String oraChiusura;
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }
        public boolean equals(Features F1) {
        	if(F1.indoor == this.indoor) {
        		if(F1.ac==false && F1.heating == false) {
        			return true;
        		}
        		if(F1.ac==false && F1.heating == true) {
        			if(this.heating==true)
        				return true;
        			return false;
        		}
        		if(F1.ac==true && F1.heating == false) {
        			if(this.ac==true)
        				return true;
        			return false;
        		}
        		if(F1.ac==true && F1.heating == true) {
        			if(this.heating==true && this.ac==true)
        				return true;
        			return false;
        		}
        	}
        	return false;
        }
    }
    private static int numero = 0;
    public void defineFields(Features... features) throws FutsalException {
        for (Features F : features) {
        	if(F.indoor == false && (F.ac == true || F.heating == true))
        		throw new FutsalException();
        }
        numero++;
        for(Features F : features) {
        	this.Campi.put(numero, new Campo(numero, F));
        	numero++;
        }
    }
    
    public long countFields() {
        return this.Campi.keySet().stream().count();
    }

    public long countIndoor() {
        return this.Campi.values().stream().filter(C -> C.getF().indoor==true).count();
    }
    
    public String getOpeningTime() {
        return this.oraApertura;
    }
    
    public void setOpeningTime(String time) {
    	this.oraApertura = time;

    }
    
    public String getClosingTime() {
        return this.oraChiusura;
    }
    
    public void setClosingTime(String time) {
    	this.oraChiusura = time;
    }

    private static int codice = 0;
    public int newAssociate(String first, String last, String mobile) {
        codice++;
        this.Associati.put(codice, new Associato(first, last, mobile));
        return codice;
    }
    
    public String getFirst(int partyId) throws FutsalException {
        if(!this.Associati.containsKey(partyId))
        	throw new FutsalException();
        return this.Associati.get(partyId).getNome();
    }
    
    public String getLast(int associate) throws FutsalException {
    	if(!this.Associati.containsKey(associate))
        	throw new FutsalException();
        return this.Associati.get(associate).getCognome();
    }
    
    public String getPhone(int associate) throws FutsalException {
    	if(!this.Associati.containsKey(associate))
        	throw new FutsalException();
        return this.Associati.get(associate).getNumTel();
    }
    
    public int countAssociates() {
        return codice;
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
    	if(!this.Campi.containsKey(field))
    		throw new FutsalException();
    	if(!this.Associati.containsKey(associate))
    		throw new FutsalException();
    	String [] O = this.oraApertura.split(":");
    	Integer I = Integer.valueOf(O[0]).intValue() * 60;
    	Integer F = Integer.valueOf(O[1]) + I;
    	O = time.split(":");
    	I = Integer.valueOf(O[0]).intValue() * 60;
    	Integer FF = I + Integer.valueOf(O[1]);
    	if(((FF.intValue() - F.intValue())%60)!=0)
    		throw new FutsalException();
    	this.Associati.get(associate).setPrenotato(field, time);
    	this.Campi.get(field).setOccupation();
    }

    public boolean isBooked(int field, String time) {
        for(Associato A : this.Associati.values()) {
        	if(A.getPrenotazioni().keySet().contains(field))
        	{
        		String [] O = A.getPrenotazioni().get(field).split(":");
        		Integer F = Integer.valueOf(O[0]) * 60 + Integer.valueOf(O[1]);
        		O = time.split(":");
        		Integer FF = Integer.valueOf(O[0]) * 60 + Integer.valueOf(O[1]);
        		F = F - FF;
        		if(F<60 && F>=0)
        			return true;
        	}
        }
        return false;
    }
    

    public int getOccupation(int field) {
        return (int) this.Associati.values().stream().filter(A -> A.getPrenotazioni().keySet().contains(field) == true).count();
    }
    
    public List<FieldOption> findOptions(String time, Features required){
        return this.Campi.entrySet().stream()
        		.filter(E -> E.getValue().getF().equals(required)==true && this.isBooked(E.getKey(), time) == false)
        		.map(E -> E.getValue())
        		.sorted((C1, C2) -> {
        			if(C1.getOccupation() == C2.getOccupation())
        				return C1.getField() - C2.getField();
        			return C2.getOccupation() - C1.getOccupation();
        		}).collect(Collectors.toList());
    }
    
    public long countServedAssociates() {
        return this.Associati.values().stream().filter(A -> A.getPrenotazioni().isEmpty()==false).count();
    }
    
    public Map<Integer,Long> fieldTurnover() {
        return this.Campi.values().stream()
        		.collect(Collectors.groupingBy(C -> C.getField(),
        				HashMap::new,
        				Collectors.summingLong((C -> C.getOccupation()))));
    }
    
    public double occupation() {
        double d = 0.0;
        for(Campo C : this.Campi.values())
        	d+=C.getOccupation();
        System.out.println(d);
        String [] O = this.oraChiusura.split(":");
        String [] O1 = this.oraApertura.split(":");
        Integer I = (Integer.valueOf(O[0])*60 + Integer.valueOf(O[1])) - (Integer.valueOf(O1[0])*60 + Integer.valueOf(O1[1])); 
        Double D = I.doubleValue()/60.0;
        return (d/(D*this.Campi.keySet().stream().count()));
    }
    
}
