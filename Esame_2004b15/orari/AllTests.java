package orari;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

class AllTests {

	@Test
	public void test1() {
		  	Orari orari = new Orari();
		  	String codice = "IC2345";
		  	String categoria = "Intercity";
		  	Percorso p = orari.creaPercorso(codice,categoria);
		  	
		  	assertTrue(p!=null);
				assertEquals(codice,p.getCodice());
				assertEquals(categoria,p.getCategoria());
				assertFalse("Un percorso per defaul NON è straordinario",
							p.isStraordinario());

				p.setStraordinario(true);
				assertTrue(p.isStraordinario());
		  
		  

				Orari orari2 = new Orari();
				String codice2[] = {"IC2345","IR432"};
				String categoria2[] = {"Intercity","Interregionale"};
				
				for(int i=0; i<codice2.length; ++i){
					orari2.creaPercorso(codice2[i],categoria2[i]);
				}
				
				Collection percorsi = orari2.getPercorsi();
				
				assertEquals(codice2.length,percorsi.size());
		  

		 
				Orari orari3 = new Orari();
				String codice3[] = {"IC2345","IR432"};
				String categoria3[] = {"Intercity","Interregionale"};
				
				for(int i=0; i<codice3.length; ++i){
					orari3.creaPercorso(codice3[i],categoria3[i]);
				}
				
				for(int i=0; i<codice3.length; ++i){
					Percorso percorso = orari3.getPercorso(codice3[i]);
					assertEquals(codice3[i],percorso.getCodice());		
				}
		  
	}
	@Test
	public void test2() {
		
			Orari orari = new Orari();
			Percorso p = orari.creaPercorso("IC2345","Intercity");
			
			String nomeStazione = "Torino Porta Nuova";
			int ore = 10;
			int minuti = 15;
			Fermata f = p.aggiungiFermata(nomeStazione,ore,minuti);
			
			assertEquals(nomeStazione,f.getStazione());
			assertEquals(ore,f.getOre());
			assertEquals(minuti,f.getMinuti());
	  

	  
			Orari orari2 = new Orari();
			Percorso p2 = orari.creaPercorso("IC2345","Intercity");
			
			p2.aggiungiFermata("Torino Porta Nuova",10,15);
			p2.aggiungiFermata("Vercelli",11,05);
			p2.aggiungiFermata("Novara",11,40);
			p2.aggiungiFermata("Milano Centrale",12,30);
			
			List fermate = p2.getFermate();
			assertEquals(4,fermate.size());		
	  

			Orari orari3 = new Orari();
			Percorso p3 = orari3.creaPercorso("IC2345","Intercity");
			
			Fermata f1 = p3.aggiungiFermata("Torino Porta Nuova",10,15);
			Fermata f2 = p3.aggiungiFermata("Vercelli",11,05);
			Fermata f3 = p3.aggiungiFermata("Novara",11,40);
			Fermata f4 = p3.aggiungiFermata("Milano Centrale",12,30);
			
			List fermate3 = p3.getFermate();
			assertSame(f1,fermate3.get(0));		
			assertSame(f2,fermate3.get(1));		
			assertSame(f3,fermate3.get(2));		
			assertSame(f4,fermate3.get(3));		
	}
	@Test
	public void test3() {
			Orari orari = new Orari();
			Percorso p = orari.creaPercorso("IC2345","Intercity");
			p.aggiungiFermata("Torino Porta Nuova",10,15);
			p.aggiungiFermata("Vercelli",11,05);
			p.aggiungiFermata("Novara",11,40);
			p.aggiungiFermata("Milano Centrale",12,30);
			
			Treno t = null;
			try {
				t = orari.nuovoTreno("IC2345",10,11,2004);
			} catch (PercorsoNonValido e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertSame(p,t.getPercorso());
			assertEquals(10,t.getGiorno());
			assertEquals(11,t.getMese());
			assertEquals(2004,t.getAnno());

		
			Orari orari2 = new Orari();
			Percorso p2 = orari2.creaPercorso("IC2345","Intercity");
			p2.aggiungiFermata("Torino Porta Nuova",10,15);
			p2.aggiungiFermata("Vercelli",11,05);
			p2.aggiungiFermata("Novara",11,40);
			p2.aggiungiFermata("Milano Centrale",12,30);
			
			try{
				Treno t1 = orari2.nuovoTreno("AA2345",10,11,2004);
				fail("Il percorso non dovrebbe essere valido");
			}catch(PercorsoNonValido pnv){
				assertTrue(true); // Ok
			}

		
			Orari orari3 = new Orari();
			Percorso p3 = orari.creaPercorso("IC2345","Intercity");
			p3.aggiungiFermata("Torino Porta Nuova",10,15);
			p3.aggiungiFermata("Vercelli",11,05);
			p3.aggiungiFermata("Novara",11,40);
			p3.aggiungiFermata("Milano Centrale",12,30);
			try {
				Treno t2 = orari3.nuovoTreno("IC2345",10,11,2004);
			} catch (PercorsoNonValido e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Treno t3 = orari3.nuovoTreno("IC2345",11,11,2004);
			} catch (PercorsoNonValido e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List treni = orari3.getTreni();
	}
	@Test
	public void test4() {
			Orari orari = new Orari();
			Percorso p = orari.creaPercorso("IC2345","Intercity");
			Fermata f1 = p.aggiungiFermata("Torino Porta Nuova",10,15);
			p.aggiungiFermata("Vercelli",11,05);
			p.aggiungiFermata("Novara",11,40);
			p.aggiungiFermata("Milano Centrale",12,30);
			Treno t = null;
			try {
				t = orari.nuovoTreno("IC2345",10,11,2004);
			} catch (PercorsoNonValido e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			Passaggio pass = null;
			try {
				pass = t.registraPassaggio("Torino Porta Nuova",10,20);
			} catch (StazioneNonValida e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			assertEquals("Torino Porta Nuova",pass.getStazione());
			assertEquals(10,pass.getOra());
			assertEquals(20,pass.getMinuti());		
		

			Orari orari2 = new Orari();
			Percorso p2 = orari2.creaPercorso("IC2345","Intercity");
			Fermata f2 = p2.aggiungiFermata("Torino Porta Nuova",10,15);
			p2.aggiungiFermata("Vercelli",11,05);
			p2.aggiungiFermata("Novara",11,40);
			p2.aggiungiFermata("Milano Centrale",12,30);
			Treno t2 = null;
			try {
				t2 = orari2.nuovoTreno("IC2345",10,11,2004);
			} catch (PercorsoNonValido e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Passaggio pass2 = null;
			try{
				pass2 = t2.registraPassaggio("Porta Susa",10,20);
				fail("La stazione non dovrebbe essere corretta");
			}catch(StazioneNonValida snv){
				assertTrue(true); // ok
			}

			Orari orari3 = new Orari();
			Percorso p3 = orari3.creaPercorso("IC2345","Intercity");
			Fermata f3 = p3.aggiungiFermata("Torino Porta Nuova",10,15);
			p3.aggiungiFermata("Vercelli",11,05);
			p3.aggiungiFermata("Novara",11,40);
			p3.aggiungiFermata("Milano Centrale",12,30);
			Treno t3 = null;
			try {
				t3 = orari3.nuovoTreno("IC2345",10,11,2004);
			} catch (PercorsoNonValido e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Passaggio pass3 = null;
			try {
				 pass3 = t3.registraPassaggio("Torino Porta Nuova",10,20);
			} catch (StazioneNonValida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			assertEquals(5,pass3.ritardo());
		}
	@Test	//Qui
	public void test5() {
			Orari orari = new Orari();
			Percorso p = orari.creaPercorso("IC2345","Intercity");
			p.aggiungiFermata("Torino Porta Nuova",10,15);
			p.aggiungiFermata("Vercelli",11,05);
			p.aggiungiFermata("Novara",11,40);
			p.aggiungiFermata("Milano Centrale",12,30);
			Treno t = null;
			try {
				t = orari.nuovoTreno("IC2345",10,11,2004);
			} catch (PercorsoNonValido e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}		
			try {
				t.registraPassaggio("Torino Porta Nuova",10,20);
			} catch (StazioneNonValida e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				t.registraPassaggio("Vercelli",11,15);
			} catch (StazioneNonValida e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				t.registraPassaggio("Novara",11,46);
			} catch (StazioneNonValida e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			assertFalse(t.arrivato());
			
			try {
				t.registraPassaggio("Milano Centrale",12,33);
			} catch (StazioneNonValida e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			assertTrue(t.arrivato());
			

			Orari orari2 = new Orari();
			Percorso p2 = orari2.creaPercorso("IC2345","Intercity");
			p2.aggiungiFermata("Torino Porta Nuova",10,15);
			p2.aggiungiFermata("Vercelli",11,05);
			p2.aggiungiFermata("Novara",11,40);
			p2.aggiungiFermata("Milano Centrale",12,30);
			Treno t2 = null;
			try {
				t2 = orari.nuovoTreno("IC2345",10,11,2004);
			} catch (PercorsoNonValido e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}		
			try {
				t2.registraPassaggio("Torino Porta Nuova",10,20);
			} catch (StazioneNonValida e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				t2.registraPassaggio("Vercelli",11,15);
			} catch (StazioneNonValida e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				t2.registraPassaggio("Novara",11,46);
			} catch (StazioneNonValida e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				t2.registraPassaggio("Milano Centrale",12,33);
			} catch (StazioneNonValida e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			assertEquals(10,t2.ritardoMassimo());
			assertEquals(3,t2.ritardoFinale());
		

			Orari orari3 = new Orari();
			Percorso p3= orari3.creaPercorso("IC2345","Intercity");
			p3.aggiungiFermata("Torino Porta Nuova",10,15);
			p3.aggiungiFermata("Vercelli",11,05);
			p3.aggiungiFermata("Novara",11,40);
			p3.aggiungiFermata("Milano Centrale",12,30);
			Treno t3 = null;
			try {
				t3 = orari3.nuovoTreno("IC2345",10,11,2004);
			} catch (PercorsoNonValido e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			try {
				t3.registraPassaggio("Torino Porta Nuova",10,20);
			} catch (StazioneNonValida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t3.registraPassaggio("Vercelli",11,15);
			} catch (StazioneNonValida e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				t3.registraPassaggio("Novara",11,46);
			} catch (StazioneNonValida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t3.registraPassaggio("Milano Centrale",12,33);
			} catch (StazioneNonValida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Treno t4 = null;
			try {
				t4 = orari3.nuovoTreno("IC2345",10,11,2004);
			} catch (PercorsoNonValido e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			try {
				t4.registraPassaggio("Torino Porta Nuova",10,21);
			} catch (StazioneNonValida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t4.registraPassaggio("Vercelli",11,8);
			} catch (StazioneNonValida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t4.registraPassaggio("Novara",11,49);
			} catch (StazioneNonValida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t4.registraPassaggio("Milano Centrale",12,35);
			} catch (StazioneNonValida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			assertEquals(10,p3.ritardoMassimo());
			assertEquals(5,p3.ritardoMedio());

	}

}
