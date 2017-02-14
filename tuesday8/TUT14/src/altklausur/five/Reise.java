package altklausur.five;

public class Reise {
	public static void main(String[] args) {
		Verkehrsmittel v = new Cessna();
		v.flieg();
		v.baujahr = 1990;
		v = new Flugzeug();
		Auto.getPreis();
		Auto b = new Auto();
		v = new Auto();
		v.bewegDich();
		b.bewegDich();
		b.getPreis();
	}
}

class Auto extends Verkehrsmittel {
	public void bewegDich() {
		System.out.println("Auto");
	}

	public int getPreis() {
		return super.getPreis();
	}
}

abstract class Flugzeug extends Verkehrsmittel {
	public static void flieg() {
		System.out.println("Hui");
	}
}

class Cessna extends Flugzeug {
}

class Verkehrsmittel {
	public String name = "";
	protected int baujahr;
	private int preis;

	protected int getPreis() {
		return this.preis;
	}

	private void bewegDich() {
		System.out.println("Bewegung");
	}
}