package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	public Model() {

	}

	public String getUmiditaMedia(int mese) {
		MeteoDAO mDAO=new MeteoDAO();
		ArrayList<Rilevamento> rilevamenti= new ArrayList<Rilevamento>(mDAO.getAllRilevamenti());
		double totale=0;
		int tempo=0;
		
		for(Rilevamento r: rilevamenti) {
			if(r.getData().getMonth()==mese) {
				tempo++;
				totale+=r.getUmidita();
			}
		}
		double media=totale/tempo;
		String ritorno=""+media;
		return ritorno;
	}

	public String trovaSequenza(int mese) {

		return "TODO!";
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		return true;
	}

}
