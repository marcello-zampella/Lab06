package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO mDAO;
	private ArrayList<Citta> insiemeLocalita;
	private ArrayList<SimpleCity> migliore;
	private double massimo;
	int sequenza;

	public Model() {

	}

	public String getUmiditaMedia(int mese) {
		mDAO=new MeteoDAO();
	//	ArrayList<Rilevamento> rilevamenti= new ArrayList<Rilevamento>(mDAO.getAllRilevamenti());

		/*double totale=0;
		int tempo=0;
		
		for(Rilevamento r: rilevamenti) {
			if(r.getData().getMonth()==mese) {
				tempo++;
				totale+=r.getUmidita();
			}
		}
		double media=totale/tempo;
		String ritorno=""+media; */
		String risultato="";
		for(String citt: mDAO.getAllLocalita()) {
			risultato+=citt.toUpperCase()+": "+mDAO.getAvgRilevamentiLocalitaMese(mese+1, citt)+"\n";
		}
		return risultato;
	}

	public String trovaSequenza(int mese) {
		 sequenza=15;
		this.mDAO=new MeteoDAO();
		ArrayList<SimpleCity> parziale= new ArrayList<SimpleCity>();
		migliore= new ArrayList<SimpleCity>();
		this.insiemeLocalita=new ArrayList<Citta>();

		massimo=99999999;
		for(String s: mDAO.getAllLocalita()) {
			Citta x= new Citta();
			x.setNome(s);
			x.setRilevamenti(mDAO.getAllRilevamentiLocalitaMese(mese+1, s));
			insiemeLocalita.add(x);
		}
		
		espandi(parziale,0,insiemeLocalita);
		String st="";
		for(SimpleCity q: migliore) {
			st+=q.getNome()+"\n";
		}
		
		return st;
	}
	
	
	
	
	

	private void espandi(ArrayList<SimpleCity> parziale, int livello, ArrayList<Citta> insiemeLocalita) {
		if(livello==sequenza) {
			double tentativo=this.punteggioSoluzione(parziale);
			if(tentativo<massimo && controllaParziale(parziale)) {
				this.massimo=tentativo;
				migliore=new ArrayList<SimpleCity>(parziale);
				System.out.println(migliore);
			}
			return;
		}
		
		for(Citta citt: insiemeLocalita) {
			SimpleCity sc= new SimpleCity(citt.getNome(),citt.getRilevamenti().get(livello).getUmidita());
			
			parziale.add(sc);
			espandi(parziale, livello+1,insiemeLocalita);
			parziale.remove(livello);
		}
		
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {
		double costoTotale=0;
		for(SimpleCity sc: soluzioneCandidata) {
			costoTotale+=sc.getCosto();
		}
		for(int i=1;i<sequenza;i++) {
			if(!(soluzioneCandidata.get(i).equals(soluzioneCandidata.get(i-1)))) {
				costoTotale+=100;
			}
		}
		
		return costoTotale;
	}
	
	private boolean controlloAlmeno3Giorni(List<SimpleCity> parziale) {
		int i=0;
		int count=0;
		while(i<=sequenza-2) {
			System.out.println(parziale.get(i)+"   "+parziale.get(i+1));
		while(parziale.get(i).equals(parziale.get(i+1))){
			i++;
			count++;
			if(i+1>=sequenza) {
				return true;
			}
		}
		if(count<2) {
			return false;
		}
		count=0;
		i++;
		}
		return true;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {
			for(Citta c: insiemeLocalita) {
				for(SimpleCity sc: parziale) {
				if(sc.getNome().equals(c.getNome())) {
					c.increaseCounter();
				}
			}
				
				if(c.getCounter()>6 || c.getCounter()==0) {
					for(Citta g: insiemeLocalita) {
						g.setCounter(0);
					}
					return false;
				}
		}
			
				for(Citta g: insiemeLocalita) {
					g.setCounter(0);
				}
		return this.controlloAlmeno3Giorni(parziale);
	}

}
