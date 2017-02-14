package altklausur.sixc;

public class Warteschlange<T> {

	private Eintrag<T> anfang;

	public T entnehme() {
		if (anfang == null)
			return null;
		Eintrag<T> ergebnis = anfang;
		anfang = ergebnis.next;
		return ergebnis.wert;
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer("[");
		Eintrag<T> aktuell = anfang;
		while (aktuell != null) {
			res.append(aktuell.wert + " ");
			aktuell = aktuell.next;
		}
		return res.toString().trim() + "]";
	}
	
	public void haengeAn(T wert) {
		if (anfang == null) {
			Eintrag<T> kopf = new Eintrag<T>();
			kopf.wert = wert;
			anfang = kopf;
			return;
		}
		
		Eintrag<T> aktuell = anfang;
		while(aktuell.next != null) {
			aktuell = aktuell.next;
		}
		
		//aktuell.next = new Eintrag<T>();
		//aktuell.next.wert = wert;
		Eintrag<T> neu = new Eintrag<>();
		neu.wert = wert;
		aktuell.next = neu;
		
	}
}
