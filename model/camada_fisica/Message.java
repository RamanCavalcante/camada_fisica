package model.camada_fisica;

import javafx.scene.control.TextArea;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2020
* Nome.............: Message.java
* Funcao...........: class objeto da usada para criar a mensagem
************************************************************** */

public class Message {
  
  String messageText;
  int protoclo;
  String messageTextoutgoing;
  TextArea saida_lbl, grafico_lbl;

/* ***************************************************************
* Metodo: Message
* Funcao: Construtor da classe
* Parametros: 
* Retorno: 
**************************************************************** */

  public Message(TextArea messageText, int protocolo, String messageTextoutgoing, TextArea saida_lbl, TextArea grafico_lbl){
    this.messageText = messageText.getText();
    this.protoclo = protocolo;
    this.messageTextoutgoing = messageTextoutgoing;
    this.saida_lbl = saida_lbl;
    this.grafico_lbl = grafico_lbl;
  }

  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }

  public int getProtoclo() {
    return protoclo;
  }

  public void setProtoclo(int protoclo) {
    this.protoclo = protoclo;
  }

  public String getMessageTextoutgoing() {
    return messageTextoutgoing;
  }

  public void setMessageTextoutgoing(String messageTextoutgoing) {
    this.messageTextoutgoing = messageTextoutgoing;
  }
  
}
