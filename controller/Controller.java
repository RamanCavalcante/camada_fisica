package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.camada_fisica.Message;
import model.camada_fisica.Rede;

public class Controller	implements Initializable {



	@FXML
	public static Button btn_enviar;

	@FXML
	public TextArea entrada_lbl;

	@FXML
	public ChoiceBox<String> menu_codificacao;

	@FXML
	public TextArea saida_lbl;

  @FXML
  public TextArea grafico_lbl;

  @FXML
  private HBox hbox_grafico;

  @FXML
  private Slider grafico_sld;

  @FXML
  private Label lbl_sldGrafico;


	public void initialize(URL location, ResourceBundle resources) {
		String [] list = {"...","Binario", "Manchester","Manchester Diferencial"};
		menu_codificacao.setItems(FXCollections.observableArrayList(list));
		menu_codificacao.setValue(list[1]);
    grafico_sld.valueProperty().addListener( (obs, oldValue, newValue) -> Rede.time = (int)(double)newValue);
	}
	
	@FXML
  void SendMessage(ActionEvent event) {
    
		int protocoloSelect = 0;
		// Encerra o enveto caso a caixa de texto esteja vazia
		if(entrada_lbl.getText().equals("")){		return;} 
      protocoloSelect = menu_codificacao.getSelectionModel().getSelectedIndex();
			Message objMessage = new Message(entrada_lbl, protocoloSelect, "", saida_lbl, grafico_lbl);
			Rede objRede = new Rede(objMessage, hbox_grafico);
			objRede.aplicacaoTransmissora();
			//btn_enviar.setDisable(true);
	}

  @FXML
  void onMouse(MouseEvent event) {
    if(event.getSource().equals(grafico_sld)){
      lbl_sldGrafico.setText(""+(int)Rede.time);
    }
  }

  

} 

