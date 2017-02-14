package altklausur.sixc;

public class Eintrag<T> {
	
	T wert;
	Eintrag<T> next;

	public Eintrag(T value) {
		this.wert = value;
	}
	
	public Eintrag() {
		
	}
	//Eintrag<T> neu = new Eintrag<>();
	//neu.wert = value
	//
	//Eintrag<T> neu = new Eintrag<>(value);
}
