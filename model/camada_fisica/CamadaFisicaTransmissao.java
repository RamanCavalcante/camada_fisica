
package model.camada_fisica;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2022
* Nome.............: CamadaFisicaTransamissora.java
* Funcao...........: Parte da camada fisica de transmissao
************************************************************** */

public class CamadaFisicaTransmissao {

/* ***************************************************************
* Metodo: camadaFisicaTransmissoraCodificacaoBinaria
* Funcao: recebe vetor de bits e realiza codificacao binaria
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] camadaFisicaTransmissoraCodificacaoBinaria(int [] quadro){
		int[] bits  = Converter.asciiToBits(quadro);

    return bits;
    
	}

/* ***************************************************************
* Metodo: camadaFisicaTransmissoraCodificacaoManchester
* Funcao: recebe vetor de bits e realiza decodificacao manchester
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] camadaFisicaTransmissoraCodificacaoManchester(int [] quadro){
		int[] bitsBrutos  = Converter.asciiToBits(quadro);
    //calcula o tamanho do vetor bitsCodificados
    int novoTamanho = 0;
    //numberOfLeadingZeros retorna a quantidade de zeros contidos a esquerda
    if(Integer.SIZE-Integer.numberOfLeadingZeros(bitsBrutos[bitsBrutos.length-1]) <= 16){
      novoTamanho = (bitsBrutos.length*2)-1;
    }else{
      novoTamanho = bitsBrutos.length*2;
    }

    int[] bitsCodificados = new int[novoTamanho];

    // cria um valor inteiro com 1 no bit mais à esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000

    //int com todos os bits 0s
    int valor = 0;//00000000 00000000 00000000 00000000

    for(int i=0, pos=0; i<bitsBrutos.length; i++){
      int numero = bitsBrutos[i];

      //pega o numero de bits do inteiro
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

      numero <<= 32-numeroDeBits; //desloca os bits para a esquerda
      for(int j=1; j<=numeroDeBits; j++){
        if((numero & displayMask) == 0){
          valor <<= 1;
          valor = valor | 0;
          valor <<= 1;
          valor = valor | 1;
        }else{
          valor <<= 1;
          valor = valor | 1;
          valor <<= 1;
          valor = valor | 0;
        }
        numero <<= 1;
        if(j == 16){
          bitsCodificados[pos] = valor;
          valor = 0;
          pos++;
        }else if(j == numeroDeBits){
          bitsCodificados[pos] = valor;
          valor = 0;
          pos++;
        }
      }
    }
		return bitsCodificados;
	}

/* ***************************************************************
* Metodo: camadaFisicaTransmissoraCodificacaoManchesterDiferencial
* Funcao: recebe vetor de bits e realiza decodificacao manchester diferencial
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] camadaFisicaTransmissoraCodificacaoManchesterDiferencial(int [] quadro){
    int[] bitsBrutos = Converter.asciiToBits(quadro);

    //calcula o tamanho do vetor bitsCodificados
    int novoTamanho = 0;
    if(Integer.SIZE-Integer.numberOfLeadingZeros(bitsBrutos[bitsBrutos.length-1]) <= 16){
      novoTamanho = (bitsBrutos.length*2)-1;
    }else{
      novoTamanho = bitsBrutos.length*2;
    }

    int[] bitsCodificados = new int[novoTamanho];

    // cria um valor inteiro com 1 no bit mais à esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000

    //int com todos os bits 0s
    int valor = 0;//00000000 00000000 00000000 00000000

    boolean transicao = false; //indentifica transicao

    for(int i=0, pos=0; i<bitsBrutos.length; i++){
      int numero = bitsBrutos[i];
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

      numero <<= 32-numeroDeBits; //desloca os bits para a esquerda

      for(int j=1; j<=numeroDeBits; j++){
        if((numero & displayMask) == 0){
          if(transicao){
            valor <<= 1;
            valor = valor | 1;
            valor <<= 1;
            valor = valor | 0;
          }else{
            valor <<= 1;
            valor = valor | 0;
            valor <<= 1;
            valor = valor | 1;
          }
        }else{
          transicao = !transicao; //houve transicao

          if(transicao){
            valor <<= 1;
            valor = valor | 1;
            valor <<= 1;
            valor = valor | 0;
          }else{
            valor <<= 1;
            valor = valor | 0;
            valor <<= 1;
            valor = valor | 1;
          }
        }
        numero <<= 1;

        if(j == 16){
          bitsCodificados[pos] = valor;
          valor = 0;
          pos++;
        }else if(j == numeroDeBits){
          bitsCodificados[pos] = valor;
          valor = 0;
          pos++;
        }
      }
    }
    return bitsCodificados;
	}
}
