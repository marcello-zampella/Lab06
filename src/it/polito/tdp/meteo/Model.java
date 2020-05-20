package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 30;
	private MeteoDAO mDAO;
	private ArrayList<Citta> insiemeLocalita;
	private ArrayList<SimpleCity> migliore;
	private double massimo;
	int sequenza;
	int contatorecaso;

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
			risultato+=citt.toUpperCase()+": "+mDAO.getAvgRilevamentiLocalitaMese(mese, citt)+"\n";
		}
		return risultato;
	}

	public String trovaSequenza(int mese) {
		contatorecaso=0;
		 sequenza=NUMERO_GIORNI_TOTALI;
		this.mDAO=new MeteoDAO();
		LinkedList<SimpleCity> parziale= new LinkedList<SimpleCity>();
		migliore= new ArrayList<SimpleCity>();
		this.insiemeLocalita=new ArrayList<Citta>();

		massimo=99999999;
		for(String s: mDAO.getAllLocalita()) {
			Citta x= new Citta();
			x.setNome(s);
			x.setRilevamenti(mDAO.getAllRilevamentiLocalitaMese(mese, s));
			insiemeLocalita.add(x);
		}
		
		espandi(parziale,0,insiemeLocalita);
		String st="";
		int conteggio=1;
		for(SimpleCity q: migliore) {
			st+="GIORNO "+conteggio+": "+q.getNome()+"\n";
			conteggio++;
		}
		System.out.println(contatorecaso);
		return st;
	}
	
	
	
	
	

	private void espandi(LinkedList<SimpleCity> parziale, int livello, ArrayList<Citta> insiemeLocalita) {
		
		if(!this.ottimizza(parziale))
			return;
		contatorecaso++;
		if(parziale.size()==sequenza) {
			double tentativo=this.punteggioSoluzione(parziale);
			if(tentativo<massimo && controllaParziale(parziale)) {
				this.massimo=tentativo;
				migliore=new ArrayList<SimpleCity>(parziale);
			}
			return;
		}
		
		for(Citta citt: insiemeLocalita) {
			SimpleCity sc= new SimpleCity(citt.getNome(),citt.getRilevamenti().get(livello).getUmidita());
			
			parziale.add(sc);
			
			
				espandi(parziale, livello+1,insiemeLocalita);
			
			parziale.removeLast();
		}
		
	}

	private boolean ottimizza(LinkedList<SimpleCity> parziale) {
		for(Citta c: insiemeLocalita) {
			for(SimpleCity sc: parziale) {
			if(sc.getNome().equals(c.getNome())) {
				c.increaseCounter();
			}
		}
			if(c.getCounter()>NUMERO_GIORNI_CITTA_MAX) {
				for(Citta g: insiemeLocalita) {
					g.setCounter(0);
				}
				return false;
			}
	}
		for(Citta g: insiemeLocalita) {
			g.setCounter(0);
		}
		
			
		int numero=parziale.size()-1;
		if(numero<=2)
			return true;
		
		if(!(parziale.get(numero).equals(parziale.get(numero-1)))) {
			if(parziale.get(numero-1).equals(parziale.get(numero-2)) && parziale.get(numero-2).equals(parziale.get(numero-3))) {
				return true;
			}
			else
				return false;
		}
		return true;
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {
		double costoTotale=0;
		for(SimpleCity sc: soluzioneCandidata) {
			costoTotale+=sc.getCosto();
		}
		for(int i=1;i<sequenza;i++) {
			if(!(soluzioneCandidata.get(i).equals(soluzioneCandidata.get(i-1)))) {
				costoTotale+=COST;
			}
		}
		
		return costoTotale;
	}
	
	private boolean controlloAlmeno3Giorni(List<SimpleCity> parziale) {
		int i=0;
		int count=0;
		while(i<=sequenza-2) {
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
				
				if(c.getCounter()>NUMERO_GIORNI_CITTA_MAX || c.getCounter()==0) {
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
