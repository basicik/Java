import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Andreas og Henriks magiske eventyr
public class Oblig4 {
	public static void main(String[] args) throws IOException {
		
		Tabell<Medication> medications = new Tabell<Medication>(10);
		
		EnkelReseptListe prescriptions = new EnkelReseptListe();
		
		SortertEnkelListe<Doctor> leger = new SortertEnkelListe<Doctor>();
		
		Tabell<Person> personer = new Tabell<Person>(10);
		while(true){
			//Input variabler
	        String noe;
	        String noe1;
	        String noe2;
	        String noe3;
	
	        int tall;
	        int tall1;
	        int tall2;
	        int tall3;
	        int tall4;
	        
	        //Scanner og hjelpetekst
	        /* 
	         * Når man leser blir bare Personer lest inn og lagt inn, den koden
	         * var mer for å vise konseptet bak innlesinga
	         */
	        Scanner s = new Scanner(System.in);
	        System.out.println("Welcome!");
	        System.out.println("Trykk 1 for sette inn et legemiddel");
	        System.out.println("Trykk 2 for sette inn en lege");
	        System.out.println("Trykk 3 for sette inn en Person");
	        System.out.println("Trykk 4 for sette inn en ny resept");
	        System.out.println("Trykk 5 for innlesing av data");
	        System.out.println("Trykk noe annet for å avslutte programmet og skrive ut data");
	        System.out.println("\n Skriv tallet her:  ");
	        
	        //Legemiddel
	        tall = s.nextInt();
	        if(tall == 1){
	            System.out.println("skriv legemiddel nr : ");
	            tall1 = s.nextInt();
	            s.nextLine();
	            System.out.println("Skriv inn legemiddel navn ");
	            noe = s.nextLine();
	
	            System.out.println("skriv legemiddel form:  ");
	            noe1  = s.nextLine();
	
	            System.out.println("skriv legemiddel type:  ");
	            noe2  = s.nextLine();
	
	            System.out.println("skriv legemiddel pris:  ");
	            tall2  = s.nextInt();
	            s.nextLine();
	            System.out.println("skriv legemiddel mengde:  ");
	            tall3  = s.nextInt();
	            s.nextLine();
	             System.out.println("skriv legemiddel styrke:  ");
	            tall4  = s.nextInt();
	            s.nextLine();
	            
	            switch(noe1){
	            case "pille":
	            	switch(noe2){
	            	case "a":
	            		MedAP medap = new MedAP(noe, tall1, tall2, tall4);
	            	case "b":
	            		MedBP medbp = new MedBP(noe, tall1, tall2, tall4);
	            	case "c":
	            		MedCP medcp = new MedCP(noe, tall1, tall2);
	            	}
	            case "injeksjon":
	            	switch(noe2){
	            	case "a":
	            		MedAI medai = new MedAI(noe, tall1, tall2, tall4);
	            	case "b":
	            		MedBI medbi = new MedBI(noe, tall1, tall2, tall4);
	            	case "c":
	            		MedCI medci = new MedCI(noe, tall1, tall2);
	            	}
	            case "liniment":
	            	switch(noe2){
	            	case "a":
	            		MedAS medas = new MedAS(noe, tall1, tall2, tall4);
	            	case "b":
	            		MedBS medbs = new MedBS(noe, tall1, tall2, tall4);
	            	case "c":
	            		MedCS medcs = new MedCS(noe, tall1, tall2);
	            	}
	            }
	
	
	        }else if(tall == 2){
	        	//Lege
	            System.out.println("Skriv in legens navn : ");
	            noe = s.next();
	            s.nextLine();
	            System.out.println("Skriv 1 eller 0: ");
	            tall1 = s.nextInt();
	            s.nextLine();
	            System.out.println("Skriv avtalenr : ");
	            tall2 = s.nextInt();
	            s.nextLine();
	            if(tall1 == 1){
	            	Doctor d = new Doctor(noe, true, tall2);
	            	leger.settInn(d);
	            }else{
	            	Doctor d = new Doctor(noe, false, tall2);
	            	leger.settInn(d);
	            }
	            
	        }
	        else if(tall == 3){
	        	//Person
	            System.out.println("Skriv in Person nr:  ");
	            tall1 = s.nextInt();
	            s.nextLine();
	            System.out.println("Skriv in Person navn:  ");
	            noe1 = s.nextLine();
	            System.out.println("Skriv in Person ting ");
	            noe2 = s.next();
	            s.nextLine();
	
	            Person p = new Person(tall1, noe1, noe2.charAt(0));
	            personer.settInn(p.id, p);
	        }
	        else if(tall == 4){
	        	//Resept
	            System.out.println("Skriv in Resept nr: ");
	            tall1 = s.nextInt();
	            s.nextLine();
	            System.out.println("Skriv in Resept h eller b: ");
	            noe1 = s.next();
	            s.nextLine();
	            System.out.println("Skriv in Personnr: ");
	            tall2 = s.nextInt();
	            s.nextLine();
	            System.out.println("Skriv in Legenavn: ");
	            noe2 = s.nextLine();
	            System.out.println("Skriv in Legemiddelnr: ");
	            tall3 = s.nextInt();
	            s.nextLine();
	            System.out.println("Skriv in Reseptens reit: ");
	            tall4 = s.nextInt();
	            s.nextLine();
	            
	            if(noe1 == "h"){
	            	Prescription p = new Prescription(tall1, true, tall2, noe2, tall3, tall4);
	            	prescriptions.settInn(p);
	            	personer.hentUt(p.pasientNr).res.settInn(p);
	            	leger.hentUt(p.doc).res.settInn(p);
	            }else{
	            	Prescription p = new Prescription(tall1, false, tall2, noe2, tall3, tall4);
	            	personer.hentUt(p.pasientNr).res.settInn(p);
	            	leger.hentUt(p.doc).res.settInn(p);
	            	prescriptions.settInn(p);
	            }
	            
	            
	        }else if(tall == 5){
	        	File file = new File("data.txt");
	    		Scanner scan = new Scanner(file);
	    		
	    		String line;
	    		
	    		scan.useDelimiter(", *|\n");
	    		scan.nextLine();
	    		
	    		for(int i = 0; i<5; i++){
	    			int id = Integer.parseInt(scan.next());
	    			String name = scan.next();
	    			char gender = (scan.next()).charAt(0);
	    			personer.settInn(id, new Person(id, name, gender));
	    		}
	    		scan.close();
	        }else{
	        	for(Person p : personer){
	        		if(p != null){
	        			System.out.println(p.name);
	        		}
	        	}
	        	break;
	        }
		}
		
	}
	

	
	public static void test(){
		Tabell<Medication> medications = new Tabell<Medication>(10);
		
		EnkelReseptListe prescriptions = new EnkelReseptListe();
		
		SortertEnkelListe<Doctor> leger = new SortertEnkelListe<Doctor>();
		
		Tabell<Person> personer = new Tabell<Person>(10);
		
		//Personer
		Person per1 = new Person(0, "Per", 'M');
		Person per2 = new Person(1, "Perr", 'F');
		Person per3 = new Person(2, "Perrr", 'M');
		Person per4 = new Person(3, "Perrrr", 'F');
		Person per5 = new Person(4, "Perrrrr", 'F');
		personer.settInn(per1.id, per1);
		personer.settInn(per2.id, per2);
		personer.settInn(per3.id, per3);
		personer.settInn(per4.id, per4);
		personer.settInn(per5.id, per5);
		
		for(Person p : personer){
			if(p == null){
				System.out.println(p);
			}else{
				System.out.println(p.name);
			}
		}
		
		//Doctors
		Doctor doc1 = new Doctor("Doc", true, 0);
		Doctor doc2 = new Doctor("Docc", true, 1);
		Doctor doc3 = new Doctor("Doccc", false, 2);
		Doctor doc4 = new Doctor("Docccc", false, 3);
		Doctor doc5 = new Doctor("Doccccc", true, 4);
		leger.settInn(doc1);
		leger.settInn(doc2);
		leger.settInn(doc3);
		leger.settInn(doc4);
		leger.settInn(doc5);
		
		for(Object o : leger){
			Doctor d = (Doctor)o;
			System.out.println(d.name);
		}
		
		//Medications
		Medication med1 = new Medication("Med", 0, 2.5);
		Medication med2 = new Medication("Medd", 1, 0);
		Medication med3 = new Medication("Meddd", 2, 7.9);
		Medication med4 = new Medication("Medddd", 3, 0);
		Medication med5 = new Medication("Meddddd", 4, 102);
		medications.settInn(med1.id, med1);
		medications.settInn(med2.id, med2);
		medications.settInn(med3.id, med3);
		medications.settInn(med4.id, med4);
		medications.settInn(med5.id, med5);
		
		for(Medication m : medications){
			if(m == null){
				System.out.println(m);
			}else{
				System.out.println(m.name);
			}
		}
		
		//Prescriptions
		Prescription pre1 = new Prescription(0, false, 0, "Doc", 0, 3);
		doc1.res.settInn(pre1);
		personer.hentUt(0).res.settInn(pre1);
		Prescription pre2 = new Prescription(1, true, 1, "Docc", 1, 4);
		doc2.res.settInn(pre2);
		personer.hentUt(1).res.settInn(pre2);
		Prescription pre3 = new Prescription(2, false, 2, "Doccc", 2, 6);
		doc3.res.settInn(pre3);
		personer.hentUt(2).res.settInn(pre3);
		Prescription pre4 = new Prescription(3, true, 3, "Docccc", 3, 2);
		doc4.res.settInn(pre4);
		personer.hentUt(3).res.settInn(pre4);
		Prescription pre5 = new Prescription(4, false, 4, "Doccccc", 4, 8);
		doc5.res.settInn(pre5);
		personer.hentUt(4).res.settInn(pre5);
		prescriptions.settInn(pre1);
		prescriptions.settInn(pre2);
		prescriptions.settInn(pre3);
		prescriptions.settInn(pre4);
		prescriptions.settInn(pre5);
		
		for(Object o : prescriptions){
			Prescription pr = (Prescription)o;
			System.out.printf("|Prescription %d|\n|Medication %s|\n|Doctor %s|\n\n", pr.id, medications.hentUt(pr.med).name, pr.doc);			
		}
	}
}



//Medication classes

interface Pill{
	int amount = 0;
}
interface Salve{
	int amount = 0;
}
interface Injection{
	int dose = 0;
}

//Superclass
class Medication{
	String name;
	int id;
	double price;
	
	Medication(String n, int i, double d){
		id = i;
		name = n;
		price = d;
	}
}

//Medication type A
class MedAP extends Medication implements Pill{
	int strength;
	MedAP(String n, int i, double p, int str){
		super(n, i, p);
		strength = str;
	}
}
class MedAS extends Medication implements Salve{
	int strength;
	MedAS(String n, int i, double p, int str){
		super(n, i, p);
		strength = str;
	}
}
class MedAI extends Medication implements Injection{
	int strength;
	MedAI(String n, int i, double p, int str){
		super(n, i, p);
		strength = str;
	}
}

//Medication type B
class MedBP extends Medication implements Pill{
	int addiction;
	MedBP(String n, int i, double p, int add){
		super(n, i, p);
		addiction = add;
	}
}
class MedBS extends Medication implements Salve{
	int addiction;
	MedBS(String n, int i, double p, int add){
		super(n, i, p);
		addiction = add;
	}
}
class MedBI extends Medication implements Injection{
	int addiction;
	MedBI(String n, int i, double p, int add){
		super(n, i, p);
		addiction = add;
	}
}
//Medication type C
class MedCP extends Medication implements Pill{
	MedCP(String n, int i, double p){
		super(n, i, p);
	}
}
class MedCS extends Medication implements Salve{
	MedCS(String n, int i, double p){
		super(n, i, p);
	}
}
class MedCI extends Medication implements Injection{
	MedCI(String n, int i, double p){
		super(n, i, p);
	}
}


class Prescription{
	int id;
	int med;
	String doc;
	int pasientNr;
	int reit;
	boolean white_blue; // True means blue, False means white
	
	Prescription(int i, boolean b, int p, String s, int m, int r){
		id = i;
		med = m;
		doc = s;
		pasientNr = p;
		reit = r;
		white_blue = b;
	}
}

interface Lik{
	public boolean same(String name);
}

class Doctor implements Comparable<Doctor>, Lik{
	String name;
	boolean special;
	int nr;
	EnkelReseptListe res;
	Doctor(String name, boolean special, int nr){
		this.name = name;
		this.special = special;
		this.nr = nr;
		res = new EnkelReseptListe();
	}
	public boolean same(String name) {
		if(this.name.equals(name)){
			return true;
		}
		return false;
	}
	public int compareTo(Doctor d) {
		return name.compareTo(d.name);
	}
}

class Person{
	String name;
	int id;
	char F_M; // Must be F or M
	EnkelReseptListe res;
	Person(int id, String name, char M_F){
		this.id  = id;
		F_M = M_F;
		this.name = name;
		res = new EnkelReseptListe();
	}
	
	public void set_id(int id){
		this.id = id;
	}
}



/*Datastrukturer
*
*
*/

interface AbstraktTabell<T>{
	boolean settInn(int index, T t);
	T hentUt(int index);
	Iterator<T> iterator();
}

interface AbstraktSortertEnkelListe<T extends Comparable & Lik> extends Comparable<T>, Lik, Iterable{
	boolean settInn(T t);
	T hentUt(String s);
	Iterator<T> iterator();
}

class Tabell<T> implements AbstraktTabell<T>, Iterable<T>{
	
	Object[] a;
	Tabell(int l){
		a = new Object[l];
	}
	
	public boolean settInn(int index, T t) {
		if(index >= 0 && index <= a.length){
			a[index] = t;
			return true;
		}else{
			return false;
		}
	}

	public T hentUt(int index) {
		if (a[index] == null){
			return null;
		}
		return (T)a[index];
	}

	public Iterator<T> iterator() {
		Iterator<T> it = new Iterator<T>(){
			private int currentIndex = 0;
			public boolean hasNext() {
				if(currentIndex == a.length){
					return false;
				}
				return true;
			}
			public T next() {
				return (T)a[currentIndex++];
			}
			public void remove() {
				a[currentIndex] = null;
			}
		};
		return it;
	}
	
}

class SortertEnkelListe<T extends Comparable & Lik> implements AbstraktSortertEnkelListe<T>{
	
	Node first;
	Node last;
	int amount;
	
	SortertEnkelListe(){
		first = null;
		amount = 0;
	}
	
	class Node{
		T data;
		Node next;
		Node(T t){
			data = t;
			next = null;
		}
	}
	
	@Override
	public int compareTo(T arg0) {
		return this.compareTo(arg0);
	}

	@Override
	public boolean same(String name) {
		return this.same(name);
	}

	@Override
	public boolean settInn(T t) {
		Node n = new Node(t);
		Node prev = first;
		if(amount == 0){
			first = n;
			first.next = null;
			amount++;
			return true;
		}else{
			prev = first;
			if(n.data.compareTo(first.data) > 0){
				n.next = first;
				first = n;
				amount++;
				return true;
			}
			for(Node current = first.next; current != null; current = current.next){
				if(n.data.compareTo(current.data) > 0){
					n.next = current.next;
					current.next = n;
					amount++;
					return true;
				}
				prev = current;
			}
			return false;
		}
	}

	@Override
	public T hentUt(String name) {
		int i = 0;
		for(Node n = first; n != null; n = n.next){
			if(n.data.same(name)){
				return n.data;
			}
		}
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = new Iterator<T>(){
			Node current = first;
			@Override
			public boolean hasNext() {
				
				return current != null;
			}

			@Override
			public T next() {
				Node n = current;
				current = current.next;
				return n.data;
			}

			@Override
			public void remove() {
				current = null;
			}
			
		};
		return it;
	}
	
}



class EnkelReseptListe implements Iterable  {
    Resept forste;
    Resept siste;
    Resept hjelp;
    Resept hjelp1;
    int i;

    public void settInn(Prescription p) {
        if(forste == null){
            forste = new Resept(p);
            siste = forste;
            hjelp = forste;
            forste.denne.id = i;
            i++;


        }else if(siste == null) {
            siste = new Resept(p);
            forste.neste = siste;
            siste.forje = forste;
            siste.denne.id = i;
            i++;
        }else {


            siste.neste = new Resept(p);
            siste.neste.forje = siste;
            siste = siste.neste;
            siste.denne.id = i;
            i++;

        }


    }

    @Override
    public Iterator iterator() {
          hjelp = forste;
        Iterator k = new Iterator(){
            @Override
            public boolean hasNext() {

                return hjelp != null;
            }

            @Override
            public Prescription next() {
                hjelp1 = hjelp;
                hjelp = hjelp.neste;
                return (Prescription)hjelp1.denne;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        return k;
    }

    public Prescription hentUt(int nr) {
        for(Resept r = forste; r != null; r = r.neste) {

            if(r.denne.id == nr) {
                return r.denne;
            }
        }

        return null;
    }
    class Resept {

        Resept forje;
        Resept neste;
        Prescription denne;

        Resept(Prescription pr) {
            denne = pr;
        }
    }

}





class EldsteForstReseptListe extends EnkelReseptListe implements Iterable {
       
    @Override
    public Iterator iterator() {
        hjelp = siste;
        Iterator i = new Iterator(){

            @Override
            public boolean hasNext() {
        
                return hjelp != null;
            }

            @Override
            public Prescription next() {
                hjelp1 = hjelp;
                hjelp = hjelp.forje;
                return (Prescription)hjelp1.denne;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
    
        };
        return i;
    }

    
}

class YngsteForstReseptListe extends EnkelReseptListe implements Iterable {
     

    @Override
    public Iterator iterator() {
        hjelp = forste;
        Iterator j = new Iterator(){
            @Override
            public boolean hasNext() {
        
                return hjelp != null;
            }

            @Override
            public Prescription next() {
                hjelp1 = hjelp;
                hjelp = hjelp.neste;
                return (Prescription)hjelp1.denne;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
    
        };
        return j;
    }
}



