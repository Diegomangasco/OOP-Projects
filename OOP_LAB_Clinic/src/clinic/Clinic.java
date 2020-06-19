package clinic;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	
	private HashMap<String, Patient> Pazienti = new HashMap<>();
	private HashMap<Integer, Doctor> Dottori = new HashMap<>();
	private HashMap<Integer, List<String>> PazientiPerDottore = new HashMap<>();
	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		// TODO Complete method
		this.Pazienti.put(ssn, new Patient(first, last, ssn));
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		// TODO Complete method
		if(this.Pazienti.containsKey(ssn)) {
			return this.Pazienti.get(ssn).getCognome() + " " +
					this.Pazienti.get(ssn).getNome() + " " + "(" +
					this.Pazienti.get(ssn).getSSN() + ")";
		}
		throw new NoSuchPatient();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		// TODO Complete method
		this.Dottori.put(Integer.valueOf(docID), new Doctor(first, last, ssn, docID, specialization));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		// TODO Complete method
		if(this.Dottori.containsKey(Integer.valueOf(docID))) {
			return this.Dottori.get(Integer.valueOf(docID)).getCognome() + " " +
					this.Dottori.get(Integer.valueOf(docID)).getNome() + " " + "(" +
					this.Dottori.get(Integer.valueOf(docID)).getSSN() + ")" + " " + "[" +
					this.Dottori.get(Integer.valueOf(docID)).getDocID() + "]"  + ":" + " " +
					this.Dottori.get(Integer.valueOf(docID)).getSpec();
		}
		throw new NoSuchDoctor();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		// TODO Complete method
		if(!this.Pazienti.containsKey(ssn)) {
			throw new NoSuchPatient();
		}
		if(!this.Dottori.containsKey(Integer.valueOf(docID))) {
			throw new NoSuchDoctor();
		}
		
		if(this.PazientiPerDottore.get(Integer.valueOf(docID))!=null) {
			List<String> Net = this.PazientiPerDottore.get(Integer.valueOf(docID));
			Net.add(ssn);
			this.PazientiPerDottore.put(Integer.valueOf(docID), Net);
		}
		else {
			List<String> Net = new ArrayList<String>();
			Net.add(ssn);
			this.PazientiPerDottore.put(Integer.valueOf(docID), Net);
		}
		
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		// TODO Complete method
		if(!this.Pazienti.containsKey(ssn)) {
			throw new NoSuchPatient();
		}
		for(Integer I : this.PazientiPerDottore.keySet()) {
			if(this.PazientiPerDottore.get(I).contains(ssn)) {
				return I.intValue();
			}
		}
		throw new NoSuchDoctor();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		// TODO Complete method
		if(!this.Dottori.containsKey(Integer.valueOf(id))) {
			throw new NoSuchDoctor();
		}
		return this.PazientiPerDottore.get(id);
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public void loadData(Reader reader) throws IOException {
		// TODO Complete method
		BufferedReader bf = null;
		try(BufferedReader bff = new BufferedReader(reader)){
			bf = bff;
		}catch(IOException I) {
			throw I;
		}
		String linea = bf.readLine();
		while(linea!=null) {
			String [] array = linea.split(";");
			for(int i = 0; i<array.length; i++)
				array[i].trim();
			switch(array[0]) {
			case "M":
				if(array.length==6)
					this.addDoctor(array[1], array[2], array[3], Integer.valueOf(array[4]).intValue(), array[5]);
				break;
			case "P":
				if(array.length==4)
					this.addPatient(array[1], array[2], array[3]);
				break;
			}
			linea = bf.readLine();
		}
		bf.close();
	}


	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		// TODO Complete method
		List<Doctor> Net = new ArrayList<>();
		for (Integer I : this.Dottori.keySet()) {
			if(!this.PazientiPerDottore.containsKey(I)) {
				Net.add(this.Dottori.get(I));
			}
		}
		return Net
				.stream()
				.sorted(new Comparator<Doctor>() {
					@Override
					public int compare(Doctor D1, Doctor D2) {
						if(D1.getCognome().compareTo(D2.getCognome())<0)
							return -1;
						else if(D1.getCognome().compareTo(D2.getCognome())==0)
							return D1.getNome().compareTo(D2.getNome());
						return 1;
					}
				})
				.map(D -> Integer.valueOf(D.getDocID()))
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		// TODO Complete method
		int count = 0;
		int sum = 0;
		for(Integer I : this.PazientiPerDottore.keySet()) {
			count++;
			sum+=this.PazientiPerDottore.get(I).size();
		}
		List<Integer> Net = new ArrayList<>();
		for(Integer I : this.Dottori.keySet()) {
			if(this.PazientiPerDottore.get(I).size()>(int)(sum/count)) {
				Net.add(I);
			}
		}
		return Net;
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		// TODO Complete method
		return this.PazientiPerDottore
				.entrySet()
				.stream()
				.collect(Collectors.toMap(P -> P.getKey(), P -> P.getValue().size()))
				.entrySet()
				.stream()
				.sorted((Entry<Integer,Integer> E1, Entry<Integer,Integer> E2)
						-> E1.getValue().compareTo(E2.getValue()))
				.collect(Collector.of(
								ArrayList<String>::new, 
								(List<String> a, Entry<Integer, Integer> E) -> a.add(Formattatore(E)), 
								(List<String> a, List<String> b) -> {a.addAll(b); return a;}, 
								(List<String> a) -> {return a;}));
	}
	private String Formattatore(Entry<Integer, Integer> E) {
		return String.format("%3d",E.getValue()) + " : " + E.getKey() + " " + 
				this.Dottori.get(E.getKey()).getCognome() + " " + this.Dottori.get(E.getKey()).getNome();
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		return this.PazientiPerDottore
				.entrySet()
				.stream()
				.collect(Collectors.toMap(E -> E.getKey(), E -> E.getValue().size()))
				.entrySet()
				.stream()
				.collect(Collectors.toMap(E -> E.getValue(), E -> this.Dottori.get(E.getKey()).getSpec()))
				.entrySet()
				.stream()
				.sorted((E1, E2) -> {
					if(E1.getKey()>E2.getKey())
						return -1;
					else if(E1.getKey()<E2.getKey())
						return 1;
					return E1.getValue().compareTo(E2.getValue());
				})
				.collect(Collector.of(
						ArrayList<String>::new,
						(List<String> a, Entry<Integer, String> E) -> a.add(Formattatore2(E)),
						(List<String> a, List<String> b) -> {a.addAll(b); return a;}, 
						(List<String> a) -> {return a;}));
	}
	private String Formattatore2(Entry<Integer, String> E) {
		return String.format("%3d", E.getKey()) + " - " + E.getValue();
	}
	
}
