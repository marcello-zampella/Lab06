package it.polito.tdp.meteo;

import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<?> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	private Model model;
	
	private ArrayList<Mese> mesi;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		Mese m=(Mese) this.boxMese.getValue();
		this.txtResult.setText(this.model.trovaSequenza(m.getNumero()));
		
	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		
		this.txtResult.clear();
		Mese m=(Mese) this.boxMese.getValue();
		String umiditaMedia=this.model.getUmiditaMedia(m.getNumero());
		this.txtResult.appendText(umiditaMedia);
		
	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
		this.popolaMenu();
		
	}

	private void popolaMenu() {
		
		mesi= new ArrayList();
		this.mesi.add( new Mese("Gen",0));
		this.mesi.add( new Mese("Feb",1));
		this.mesi.add( new Mese("Mar",2));
		this.mesi.add(new Mese("Apr",3));
		this.mesi.add( new Mese("Mag",4));
		this.mesi.add(new Mese("Giu",5));
		this.mesi.add(new Mese("Lug",6));
		this.mesi.add(  new Mese("Ago",7));
		this.mesi.add( new Mese("Set",8));
		this.mesi.add(new Mese("Ott",9));
		this.mesi.add(new Mese("Nov",10));
		this.mesi.add(new Mese("Dic",11));
		ObservableList observableList = FXCollections.observableList(mesi);
		this.boxMese.getItems().addAll(observableList);
		
	}

	public void set(Model model) {
		this.model=model;
		// TODO Auto-generated method stub
		
	}

}
