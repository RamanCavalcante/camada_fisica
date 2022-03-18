package model.camada_fisica;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2020
* Nome.............: Converter.java
* Funcao...........: Classe onde eh realizada todas as conversoes
************************************************************** */

public class Converter{


/* ***************************************************************
* Metodo: asciiToBits
* Funcao: converte o vetor de ascii para bits
* Parametros: um vetor de inteiros
* Retorno: um vetor de inteiros
**************************************************************** */

  public static int[] asciiToBits(int[] quadro) {
    //calcula o tamanho do vetor fluxoBrutoDeBits
    int novoTamanho = 0;
    if(quadro.length%4 == 0){
      novoTamanho = quadro.length/4;
    }else{
      novoTamanho = (quadro.length/4)+1;
    }

    int[] fluxoBrutoDeBits = new int[novoTamanho];
    int valor = 0; //valor com capacidade para armazenar ate 4 caracteres

    for(int i=0, pos=0; i<quadro.length; i++){
      valor <<= 8; //desloca 8 bits para esquerda
      valor = valor | quadro[i]; //recebe em binario o valor de quadro[i]

      if(i%4 >= 0 && i == quadro.length-1){ //caso o vetor tenha menos de 4 numeros
        fluxoBrutoDeBits[pos] = valor;
      }else if(i%4 == 3){ //a cada 4 iteracoes
        fluxoBrutoDeBits[pos] = valor;
        valor=0; //reseta a variavel
        pos++;
      }
    }
    return fluxoBrutoDeBits;
  }

  public static String asciiParaMensagem(int[] quadro) {
    StringBuilder strMensagem = new StringBuilder();

    for(int i=0; i<quadro.length; i++){
       strMensagem.append(Character.toString((char)quadro[i]));
    }
    return strMensagem.toString();
  }


/* ***************************************************************
* Metodo: asciiToString
* Funcao: converte o vetor de ascii para string
* Parametros: um vetor de inteiros, e o tipo do formato
* Retorno: mensagem em string
**************************************************************** */

    public static String asciiToString(int[] quadro, int tipoDeFormato) {
      StringBuilder strAscii = new StringBuilder();
  
      if(tipoDeFormato == 0){ //tipoDeFormato == ASCII
        for(int i=0; i<quadro.length; i++){
          if(i == quadro.length-1){
            strAscii.append(Character.toString((char)quadro[i])+"->"+quadro[i]);
          }
          else{
            strAscii.append(Character.toString((char)quadro[i])+"->"+quadro[i]+" ");
          }
        }
      }
      else{ //tipoDeFormato == ASCII_DECODIFICADO
        for(int i=0; i<quadro.length; i++){
          if(i == quadro.length-1){
            strAscii.append(quadro[i]+"->"+Character.toString((char)quadro[i]));
          }
          else{
            strAscii.append(quadro[i]+"->"+Character.toString((char)quadro[i])+" ");
          }
        }
      }
      return strAscii.toString();
    }

/* ***************************************************************
* Metodo: asciiToString
* Funcao: converte o vetor de ascii para string
* Parametros: um vetor de inteiros
* Retorno: mensagem em string
**************************************************************** */

    public static String asciiToMensagem(int [] quadro){
      StringBuilder mensage = new StringBuilder();
      for(int i = 0; i < quadro.length; i++){
        mensage.append(Character.toString((char)quadro[i]));
      }
      return mensage.toString();
    }

/* ***************************************************************
* Metodo: bitsToString
* Funcao: convete um vetor de bits para string
* Parametros: um vetor de inteiros
* Retorno: uma string de bits
**************************************************************** */
  
    public static String bitsToString(int[] bits) {
      StringBuilder strBits = new StringBuilder();
  
      int displayMask = 1 << 31;
  
      for(int i=0; i<bits.length; i++){
        int numero = bits[i];
        int numeroDeBits = Integer.SIZE-Integer.numberOfLeadingZeros(numero);
  
        //arredondando o numero de bits
        if(numeroDeBits <= 8){
          numeroDeBits = 8;
        }else if(numeroDeBits <= 16){
          numeroDeBits = 16;
        }else if(numeroDeBits <= 24){
          numeroDeBits = 24;
        }else if(numeroDeBits <= 32){
          numeroDeBits = 32;
        }
  
        numero <<= 32-numeroDeBits; //desloca para os primeiros 8 bits
  
        for(int j=1; j<=numeroDeBits; j++){
          strBits.append((numero & displayMask) == 0 ? 0 : 1);
          numero <<= 1;
  
          if(j%8 == 0){
            strBits.append(" ");
          }
        }
      }
  
      return strBits.toString();
    }

/* ***************************************************************
* Metodo: bitsToAscii
* Funcao: converte o fluxo de bits brutos para ascii
* Parametros: um vetor de inteiros
* Retorno: um vetor de inteiros
**************************************************************** */

    public static int[] bitsToAscii(int[] fluxoBrutoDeBits) {
      int novoTamanho = 0;
      int numeroDeBits =
      Integer.SIZE-Integer.numberOfLeadingZeros(fluxoBrutoDeBits[fluxoBrutoDeBits.length-1]);
  
      //calculando novo tamanho do vetor quadro
      if(numeroDeBits<=8){
        novoTamanho = (fluxoBrutoDeBits.length*4)-3;
      }else if(numeroDeBits<=16){
        novoTamanho = (fluxoBrutoDeBits.length*4)-2;
      }else if(numeroDeBits<=24){
        novoTamanho = (fluxoBrutoDeBits.length*4)-1;
      }else if(numeroDeBits<=32){
        novoTamanho = fluxoBrutoDeBits.length*4;
      }
  
      int[] quadro = new int[novoTamanho];
      int displayMask = 1 << 31;
      int valor = 0;
  
      for(int i=0, pos=0; i<fluxoBrutoDeBits.length; i++){
        int numero = fluxoBrutoDeBits[i];
        numeroDeBits = Integer.SIZE-Integer.numberOfLeadingZeros(numero);
  
        //arredondando o numero de bits
        if(numeroDeBits <= 8){
          numeroDeBits = 8;
        }else if(numeroDeBits <= 16){
          numeroDeBits = 16;
        }else if(numeroDeBits <= 24){
          numeroDeBits = 24;
        }else if(numeroDeBits <= 32){
          numeroDeBits = 32;
        }
  
        numero <<= 32-numeroDeBits; //desloca para os primeiros 8 bits
  
        for(int j=1; j<=numeroDeBits; j++){
          if((numero & displayMask) == 0){
            valor <<= 1;
            valor = valor | 0;
          }else{
            valor <<= 1;
            valor = valor | 1;
          }
          numero <<= 1;
  
          if(j%8 == 0){ //um caractere
            quadro[pos] = valor;
            valor = 0;
            pos++;
          }
        }
      }
  
      return quadro;
    }
  }