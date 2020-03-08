package it.polito.tdp.meteo;

import java.net.URL;
import java.time.Month;
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
	private ChoiceBox<Month> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	private Model model;
	

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		Month m= this.boxMese.getValue();
		this.txtResult.setText(this.model.trovaSequenza(m.getValue()));
		System.out.println(System.nanoTime());
		
	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		
		this.txtResult.clear();
		Month m=(Month) this.boxMese.getValue();
		
		String umiditaMedia=this.model.getUmiditaMedia(m.getValue());
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
		
		for(int mese=1;mese<13;mese++) {
		this.boxMese.getItems().add(Month.of(mese));
		}
		
	}

	public void set(Model model) {
		this.model=model;
		// TODO Auto-generated method stub
		
	}

}
