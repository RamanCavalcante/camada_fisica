
package model.camada_fisica;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2020
* Nome.............: CamadaFisicaRecepcao.java
* Funcao...........: Parte da camada fisica de recepcao
************************************************************** */

public class CamadaFisicaRecepcao {

/* ***************************************************************
* Metodo: camadaFisicaReceptoraCodificacaoBinaria
* Funcao: recebe vetor de bits e realiza decodificacao binaria
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */
	
  public int [] camadaFisicaReceptoraCodificacaoBinaria (int [] quadro) {
    return Converter.bitsToAscii(quadro);	
  }

/* ***************************************************************
* Metodo: camadaFisicaReceptoraDecodificacaoManchester
* Funcao: recebe vetor de bits e realiza decodificacao manchester
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] camadaFisicaReceptoraDecodificacaoManchester(int [] quadro){
    
    int newSize = 0;
    int numeroDeBits =
    Integer.SIZE-Integer.numberOfLeadingZeros(quadro[quadro.length-1]);

    //calcula novo tamanho do vetor quadro
    if(numeroDeBits <= 16){
      newSize = (quadro.length*2)-1;
    }else{
      newSize = quadro.length*2;
    }

    int[] bitsDecodificados = new int[newSize];
    int displayMask = 1 << 31;
    int valor = 0;

    for(int i=0, pos=0; i<quadro.length; i++){
      int numero = quadro[i];
      numeroDeBits = Integer.SIZE-Integer.numberOfLeadingZeros(numero);

      //arredondando o numero de bits
      if(numeroDeBits <= 16){
        numeroDeBits = 16;
      }else{
        numeroDeBits = 32;
      }

      numero <<= 32-numeroDeBits; //posiciona os bits mais a esquerda

      for(int j=1; j<=numeroDeBits/2; j++){
        if((numero & displayMask) == 0){ //representa o bit 0
          valor <<= 1;
          valor = valor | 0;
        }else{ //representa o bit 1
          valor <<= 1;
          valor = valor | 1;
        }
        numero <<= 2;

        if(j%8 == 0){ //a cada 8 bits
          bitsDecodificados[pos] = valor;
          valor = 0; //reseta o inteiro
          pos++;
        }
      }
    }
    return bitsDecodificados;
  }

/* ***************************************************************
* Metodo: CamadaFisicaReceptoraDecodificacaoManchesterDiferencial
* Funcao: realiza decodificacao manchester diferencial
* Parametros: vetor de inteiros 
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] CamadaFisicaReceptoraDecodificacaoManchesterDiferencial (int [] quadro){
    int newSize = 0;
    int numeroDeBits =
    Integer.SIZE-Integer.numberOfLeadingZeros(quadro[quadro.length-1]);

    //calcula novo tamanho do vetor quadro
    if(numeroDeBits <= 16){
      newSize = (quadro.length*2)-1;
    }else{
      newSize = quadro.length*2;
    }

    int[] bitsDecodificados = new int[newSize];
    int displayMask = 1 << 31;
    int valor = 0;
    boolean transicao = false;

    for(int i=0, pos=0; i<quadro.length; i++){
      int numero = quadro[i];
      numeroDeBits = Integer.SIZE-Integer.numberOfLeadingZeros(numero);

      //arredondando o numero de bits
      if(numeroDeBits <= 16){
        numeroDeBits = 16;
      }else{
        numeroDeBits = 32;
      }

      numero <<= 32-numeroDeBits; //posiciona os bits mais a esquerda

      for(int j=1; j<=numeroDeBits/2; j++){
        if((numero & displayMask) == 0){
          if(transicao){
            valor <<= 1;
            valor = valor | 1;

            transicao = !transicao; //reseta a transicao
          }else{
            valor <<= 1;
            valor = valor | 0;
          }
        }else{
          transicao = !transicao; //houve transicao

          if(transicao){
            valor <<= 1;
            valor = valor | 1;
          }else{
            valor <<= 1;
            valor = valor | 0;

            transicao = !transicao; //reseta a transicao
          }
        }
        numero <<= 2;

        if(j%8 == 0){ //a cada 8 bits
          bitsDecodificados[pos] = valor;
          valor = 0; //reseta o inteiro
          pos++;
        }
      }
    }
    return bitsDecodificados;
	}

}	
