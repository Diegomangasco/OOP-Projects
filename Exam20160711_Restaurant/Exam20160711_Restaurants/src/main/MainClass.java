package main;

import restaurantChain.*;

public class MainClass {

	public static void main(String[] args) {
		Chain ch = new Chain();
		try {
			ch.addRestaurant("Mac1", 8);
			ch.addRestaurant("Mac2", 9);
			ch.addRestaurant("Mac3", 5);
		} catch (InvalidName e1) {
			e1.printStackTrace();
		}
		try {
			Restaurant r1 = ch.getRestaurant("Mac1");
			r1.addMenu("m1", 12.5);
			r1.addMenu("m2", 20.0);
			r1.addMenu("m3", 25.0);
			
			System.out.println("tavoli Piero: "+ r1.reserve("Piero", 2)); // 1
			System.out.println("tavoli Chiara: "+ r1.reserve("Chiara", 3)); // 1
			System.out.println("tavoli Mario: "+ r1.reserve("Mario", 4)); // 1
			System.out.println("tavoli Giorgio: "+ r1.reserve("Giorgio", 1)); // 1
			System.out.println("tavoli Anna: "+ r1.reserve("Anna", 9)); // 3
			System.out.println("tavoli Susanna: "+ r1.reserve("Susanna", 5)); // 0
			System.out.println("Persone non accolte: "+r1.getRefused()); // 5
			System.out.println("Tavoli inutilizzati: "+r1.getUnusedTables()); // 1
			
			r1.order("Piero", "m1","m2");
			r1.order("Chiara", "m1","m2");
			r1.order("Giorgio", "m1","m2");
			r1.order("Mario", "m1","m2","m1","m3");
			System.out.println("Non hanno ordinato: "+r1.getUnordered()); // [Anna, Chiara]
			
			System.out.println("Giorgio ha pagato "+ r1.pay("Giorgio"));	// 32.5			
			System.out.println("Mario ha pagato "+ r1.pay("Mario"));	// 70.0			
			System.out.println("Hanno ordinato ma non pagato: "+r1.getUnpaid()); // [Piero]
			System.out.println("Incasso totale: "+r1.getIncome()); // 102.5			
		} catch (InvalidName e) {
			e.printStackTrace();
		}
		try {
			Restaurant r2 = ch.getRestaurant("Mac2");
			r2.addMenu("m1", 15.0);
			r2.addMenu("m2", 20.0);
			r2.addMenu("m3", 25.0);
			r2.addMenu("m4", 28.0);
			
			r2.reserve("A", 3);
			r2.reserve("B", 2);
			r2.reserve("C", 1);
			r2.reserve("D", 2);
			
			r2.order("A", "m1","m2", "m3", "m1");
			r2.order("B", "m1","m2");
			r2.order("C", "m4");
			r2.order("D", "m1","m3");
			
			r2.pay("A");
			r2.pay("B");
			r2.pay("C");
			r2.pay("D");
		} catch (InvalidName e) {
			e.printStackTrace();
		}
		try {
			Restaurant r3 = ch.getRestaurant("Mac3");
			r3.reserve("Mario", 11);
			r3.reserve("Anna", 9);
		} catch (InvalidName e) {
			e.printStackTrace();
		}
		System.out.println("Ristoranti ordinati per incassi decrescenti: ");
		for(Restaurant r: ch.sortByIncome())
			System.out.println(r.getName()+ " - incasso: "+r.getIncome());
	/*
	Ristoranti ordinati per incassi decrescenti: 
	Mac2 - incasso: 178.0
	Mac1 - incasso: 102.5
	Mac3 - incasso: 0.0
	*/
		System.out.println("Ristoranti ordinati per clienti-non-accolti crescenti: ");
		for(Restaurant r: ch.sortByRefused())
			System.out.println(r.getName()+ " - clienti-non-accolti: "+r.getRefused());
	/*
	Ristoranti ordinati per clienti-non-accolti crescenti: 
	Mac2 - clienti-non-accolti: 0
	Mac1 - clienti-non-accolti: 5
	Mac3 - clienti-non-accolti: 9
	*/
		System.out.println("Ristoranti ordinati per tavoli-non-usati crescenti: ");
		for(Restaurant r: ch.sortByUnusedTables())
			System.out.println(r.getName()+ " - tavoli-non-usati: "+r.getUnusedTables());
	/*
	Ristoranti ordinati per tavoli-non-usati crescenti: 
	Mac1 - tavoli-non-usati: 1
	Mac3 - tavoli-non-usati: 2
	Mac2 - tavoli-non-usati: 5
	*/
	}
}
