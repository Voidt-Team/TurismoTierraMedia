package TurismoTierraMedia;
import java.util.Comparator;


public class ComparadorAtracciones implements Comparator<Atraccion>{
	
		
		public int compare(Atraccion a1, Atraccion a2) {

			Double costo1 = a2.getCosto();
			Double costo2 = a1.getCosto();
	        int resComp = costo1.compareTo(costo2);

	        if (resComp != 0) {
	           return resComp;
	        } 

	        Double tiempo1 = a2.getTiempo();
	        Double tiempo2 = a1.getTiempo();
	        return tiempo1.compareTo(tiempo2);
		}

}
