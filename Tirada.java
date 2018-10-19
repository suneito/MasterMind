/*
 * Tirada.java
 * 2018-02-28
 * 
 * Copyright 2018 Adrià Babot <daw1ababotllinares@iesjoanramis.org>
 * 
 */
 import java.io.*;
 /**
  * Classe que conté els mètodes necessaris per a una tirada concreta.
  */

public class Tirada {
	/**
	 * Determina el nombre la partida propietaria de la tirada
	 */
	int partidaIdTirada;
	/**
	 * Determina la mida (en digits) del nombre aleatori
	 */
	private static byte SIZE;
	
	/**
	 * Nombre resposta introduida per l'usuari
	 */
	private byte[] resposta;	
	
	/**
	 * Taula d'ajuda al usuari. 0 no correcte - 1 correcte - 2 mala posició 
	 */
	private byte taula[];
	
	/**
	 * Nombre de digits ben poscicionats
	 */
	private byte be;
	
	/**
	 * Nombre de digits mal posicionats
	 */
	private byte mal;
	
	/**
	 * Constructor d'objectes tirada nova.
	 */
	public Tirada(byte[] r, byte[] aleatori, byte size,int idPartida){
		SIZE = size;
		partidaIdTirada=idPartida;
		resposta=new byte [SIZE];
		taula=new byte[SIZE];
		
		resposta=r;
		positionOk(aleatori);
		positionNok(aleatori);
	}
	
	/**
	 * Constructor d'objectes tirada guardada previament.
	 */	
	public Tirada(String a, int b, String c, int d, int e){
		SIZE =(byte)b;
		resposta=toBytes(Partida.farcit(a,b),SIZE);
		taula=toBytes(Partida.farcit(c,b),SIZE);
		be=(byte)d;
		mal=(byte)e;
	}
	
	/**
	 * Mètode que calcula e inserta a la taula d'ajuda els nombres ben posicionats i recompta els digits ben posicionats
	 */
	private void positionOk(byte[] n){
		for (byte i = 0; i < SIZE; i++) {
			if(resposta[i]==n[i]){
				taula[i]=49;
			}else{
				taula[i]=48;
			}
		}
		for (int i = 0; i < SIZE; i++) {
			if (taula[i]==49) {
				++be;
			}
		}
	}
	
	/**
	 * Mètode que calcula e inserta a la taula d'ajuda els nombres mal posicionats i recompta els digits mal posicionats
	 */
	private void positionNok(byte[] n){
		boolean test;

		for (int i = 0; i < SIZE; i++) {
			test=true;
			if(taula[i]!=49){
				for (int j = 0;test& j < SIZE; j++) {
					if(test&n[i]==resposta[j]){
						if(taula[j]==48){
							taula[j]=50;
							test=false;
						}
					}
				}
			}
		}
		for (int i = 0; i < SIZE; i++) {
			if (taula[i]==50) {
				++mal;
			}
		}
	}
	
	/**
	 * Mètode que converteix un nombre dins String en una taula de bytes. 
	 */
	protected static byte[] toBytes(String r, int mida){
		String[] temp = r.split("");
		byte[] bytes=new byte[mida];
		
		for (int i = 0; i < mida; i++) {
			bytes[i]=(byte)(Byte.parseByte(temp[i])+48);
		}
		return bytes;
	}
	
	/**
	 * Mètode que prepara una tirada per a ser exportada
	 */
	protected String exportaTirada(){
		return "null,"+partidaIdTirada+","+SIZE+","+new String(resposta)+
			","+new String(taula)+","+be+","+mal;
	}
	
	/**
	 * Mètode de sobreescriptura (Object) per imprimir un objecte.
	 */
	public String toString(){
		return new String(resposta)+"\t"+mal+"\t"+be+"\t"+new String(taula);

	}
	
}


