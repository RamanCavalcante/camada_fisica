package model.camada_fisica;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import controller.Display;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2020
* Nome.............: Rede.java
* Funcao...........: Onde acontece todas as camadas fisicas
************************************************************** */

public class Rede {

  int protocolo;
  String strMensagem;
  Message mensagem;
  TextArea saida_lbl, grafico_lbl;
  private static int[] fluxoBrutoDeBits;
  HBox hbox_grafico;
  public static int time = 2000;

  public Rede() {
  }

/* ***************************************************************
* Metodo: Rede
* Funcao: Construtor da classe
* Parametros: objeto do tipo Message, objeto HBox para o grafico
* Retorno: 
**************************************************************** */
  
  public Rede(Message objmessage, HBox hbox_grafico) {
    this.strMensagem = objmessage.messageText;
    this.protocolo = objmessage.protoclo;
    this.saida_lbl = objmessage.saida_lbl;
    this.grafico_lbl = objmessage.grafico_lbl;
    this.hbox_grafico = hbox_grafico;

  }

  public void aplicacaoTransmissora() {

    
    camadaDeAplicacaoTransmissora(strMensagem);
  }

/* ***************************************************************
* Metodo: camadaDeAplicacaoTransmissora
* Funcao: transforma a mensagem recebida em um vetor de char e chama a camadaFisicaTransmissora
* Parametros: objeto do tipo Message, objeto HBox para o grafico
* Retorno: void
**************************************************************** */

  private void camadaDeAplicacaoTransmissora(String mensagem) {
    // TRABALHAR COM BITS
    // TRABALHAR COM BITS
    // int quadro [] = mensagem //trabalhar com bits!!!
    int quadro[] = new int[mensagem.length()];// declara e instancia o vetor quadro
    for (int i = 0; i < mensagem.length(); i++) {// laco para preencher o vetor
      quadro[i] = mensagem.charAt(i);// atribui cada letra em uma posicao
    }
    camadaFisicaTransmissora(quadro, protocolo);// chama a proxima camada
  }// fim do metodo CamadaDeAplicacaoTransmissora

/* ***************************************************************
* Metodo: camadaFisicaTransmissora
* Funcao: escolhe qual o tipo de codificao sera usada e chama o meioDeComunicacao
* Parametros: vetor de inteiros, e o tipo de codificacao
* Retorno: void
**************************************************************** */

  private void camadaFisicaTransmissora(int[] quadro, int protoclo) {
    CamadaFisicaTransmissao objTR = new CamadaFisicaTransmissao();
    int tipoDeCodificacao = protoclo; // alterar de acordo o teste
  
    switch (tipoDeCodificacao) {
      case 1: // codificao binaria
        fluxoBrutoDeBits = objTR.camadaFisicaTransmissoraCodificacaoBinaria(quadro);

        break;
      case 2: // codificacao manchester
        fluxoBrutoDeBits = objTR.camadaFisicaTransmissoraCodificacaoManchester(quadro);
        break;
      case 3: // codificacao manchester diferencial
        fluxoBrutoDeBits = objTR.camadaFisicaTransmissoraCodificacaoManchesterDiferencial(quadro);
        break;
    }

    meioDeComunicacao(fluxoBrutoDeBits);
  }

/* ***************************************************************
* Metodo: meioDeComunicacao
* Funcao: passa o fluxo bruto de bits do ponto A para o ponto B
* Parametros: vetor de inteiros
* Retorno: void
**************************************************************** */

  private void meioDeComunicacao(int[] fluxoBrutoDeBits) {
    int[] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
    int[] fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBitsPontoA.length];

    new Thread(new Runnable() {
      int valor = 0;
      int displayMask = 1 << 31;

      @Override
      public void run() {

        // passando bit a bit de um vetor para outro
        for (int i = 0; i < fluxoBrutoDeBitsPontoA.length; i++) {
          int numero = fluxoBrutoDeBitsPontoA[i];

          for (int j = 1; j <= 32; j++) {
            if ((numero & displayMask) == 0) {
              valor <<= 1;
              valor = valor | 0;
            } else {
              valor <<= 1;
              valor = valor | 1;
            }
            numero <<= 1;
          }

          fluxoBrutoDeBitsPontoB[i] = valor;
          valor = 0;
        }

        String strBits = Converter.bitsToString(fluxoBrutoDeBitsPontoB);
        int [] bits = new int [strBits.length()];
        for (int i = 0; i < strBits.length(); i++) {
          if (Character.getNumericValue(strBits.charAt(i)) != -1) { // nao pega o espaco
            bits [i] = Character.getNumericValue(strBits.charAt(i));
          }
        }

        Display.show(bits, hbox_grafico, time, new Quadro() {

          @Override
          public void fazer() {
            camadaFisicaReceptora(fluxoBrutoDeBitsPontoB, protocolo);
          }
        });
      }
    }).start();

  }

/* ***************************************************************
* Metodo: camadaFisicaReceptora
* Funcao: escolhe qual o tipo de decodificacao sera usada e chama o camadaDeAplicacaoReceptora
* Parametros: vetor de inteiros, e o tipo de codificacao
* Retorno: void
**************************************************************** */

  private void camadaFisicaReceptora(int[] quadro, int protocolo) {

    CamadaFisicaRecepcao objRE = new CamadaFisicaRecepcao();
    int tipoDeCodificacao = protocolo;

    switch (tipoDeCodificacao) {
      case 1: // codificao binaria
        fluxoBrutoDeBits = objRE.camadaFisicaReceptoraCodificacaoBinaria(quadro);
        break;
      case 2: // codificacao manchester
        fluxoBrutoDeBits = objRE.camadaFisicaReceptoraDecodificacaoManchester(quadro);
        break;
      case 3: // codificacao manchester diferencial
        fluxoBrutoDeBits = objRE.CamadaFisicaReceptoraDecodificacaoManchesterDiferencial(quadro);
        break;
    }// fim do switch/case
     // chama proxima cama
    camadaDeAplicacaoReceptora(fluxoBrutoDeBits);
  }

  private void camadaDeAplicacaoReceptora(int[] quadro) {
    // string mensagem = quadro []; //estava trabalhando com bits
    // chama proxima camada

    String mensagem = Converter.asciiToMensagem(quadro);
    aplicacaoReceptora(mensagem);
  }

  private void aplicacaoReceptora(String mensagem) {
    // exibe a mensagem
    // exibe a mensagem
    // exibe a mensagem
    saida_lbl.setText(mensagem);
  }

}
