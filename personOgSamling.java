import java.util.*;

/* Program of different Persons that collects different objects
   Gives gifts to persons on what their interests are */ 


/* Test class  including the main methode */
class test{
	
	public static void main(String args[]) {
		
		
		/* Defining different persons objects */
		Person[] p = new Person[10];
		p[0] = new Person("kurt");
		p[1] = new Person("kur");
		p[2] = new Person("kut");
		p[3] = new Person("krt");
		p[4] = new Person("urt");
		p[5] = new Person("bertil");
		p[6] = new Person("kari");
		p[7] = new Person("karsten");
		
		/* Defining what the persons collect or are very interested in */
		p[0].samlerAv("bok", 5); 
		p[1].samlerAv("bok", 5); 
		p[2].samlerAv("bok", 5); 
		p[0].samlerAv("plate", 5); 
		p[1].samlerAv("plate", 5); 
		p[2].samlerAv("plate", 5); 
		p[0].megetInteressertI("Queen");
		p[0].megetInteressertI(1946);
		p[1].megetInteressertI("Silya Nymoen");
		p[2].megetInteressertI(1900);
		p[3].samlerAv("plate", 5);
		p[3].megetInteressertI("Bob Dylan");
		p[4].samlerAv("plate", 5); 
		p[5].samlerAv("bok", 5);
		
		
		Bok[] b = new Bok[10];
		Plate[] c = new Plate[10];

		/* Creating book objects */
		b[0] = new Bok("H. Vikan", "AwesomeBok", 1899);
		b[1] = new Bok("H. Vikan", "AwesomeBok", 1920);
		b[2] = new Bok("H. Vikan", "AwesomeBok", 1899);

		/* creating CD objects */
		c[0] = new Plate("Queen", "Bohemian RapSodi", 1);
		c[1] = new Plate("Bob Dylan", "Hits 4 kids", 10);
		c[2] = new Plate("Totem", "Slippes Snart", 10);
		
		/* See if a person wants a gift */
		p[0].vilDuHaGaven(c[0]);
		p[3].vilDuHaGaven(c[1]);
		p[1].vilDuHaGaven(b[2]);

		
	}
}

/* Person class */
class Person {
	
	/* Defining the attributes of person */
	private String navn;
	private Bok[] bok;
	private Plate[] plate;
	private	String interesse;
	private int alder;	
	int bOk;
	int plAte; 
	int aBok;
	int aPlate;
	Bok bokhjelp;
	Plate platehjelp;
	
	/* Contstructor for Person, only requires name */
	Person(String s) {
		navn = s;
	}

	
	/* What the person collects */
	public void samlerAv(String smpl, int ant) {
				
			
		if(smpl.equals("plate")) {
			plAte = 1;		
			plate = new Plate[ant];
		}else if(smpl.equals("bok")){		
			bOk = 1;
			bok = new Bok[ant];

		}
		
	}
	
	//interest defined with artist for CD
	public void megetInteressertI(String artist){
		if(1 == plAte){
			interesse = artist;
		}
	}
	
	//Interest defined with age for book
	public void megetInteressertI(int eldreEnn) {
		if(1 == bOk) {
			alder = eldreEnn;
		}
	}

	//Decide if the person wants a gift
	public Bok vilDuHaGaven(Bok b) {
		if(bOk!= 1) {
		return b;
		}		

		if(aBok<(2/bok.length)) {			
			if(b.getYear() < alder) {
				return b;
			
			}			
			if(aBok>=bok.length){			
				for(int i = 0; i<bok.length; i++) {
					if(bok[i].getYear() < alder) {
						bokhjelp = bok[i];
						bok[i] = b;
						return bokhjelp;
					}	
				}
				return b;
			}
			bok[aBok] = b;
			return null;
		}else{
			bok[aBok] = b;
			return null;		
		}	
	}

		public Plate vilDuHaGaven(Plate p) {
		if(plAte!= 1) {
		return p;
		}		

		if(aPlate<(2/plate.length)) {			
			if(interesse != p.getArtist()) {
				return p;
			
			}			
			if(aPlate >= plate.length){			
				for(int i = 0; i<plate.length; i++) {
					if(plate[i].getArtist() != p.getArtist()) {
						platehjelp = plate[i];
						plate[i] = p;
						return platehjelp;
					}	
				}
				return p;
			}
			plate[aPlate] = p;
			return null;
		}else{
			plate[aPlate] = p;
			return null;		
		}	
	}
	

}

class Bok{

	private String forfatter;
	private String tittel;
	private int year;

	Bok(String f, String t, int i) {
		forfatter = f;
		tittel = t;
		year = i;
	}
	public int getYear() { return year;}
	

}

class Plate{
	
	private String artist;
	private String tittel;
	private int antspor;

	Plate(String a, String t, int i) {
		artist = a;
		tittel = t;
		antspor = i;
	}


	public String getArtist() {
		return artist;
	}
}





