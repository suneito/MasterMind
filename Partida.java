/*
 * Partida.java
 * 2018-02-28
 * 
 * Copyright 2018 Adrià Babot <daw1ababotllinares@iesjoanramis.org>
 * 
 */
 
 /**
  * Aquesta classe disposa genera un nombre aleatori per a cada objecte 
  * nou. Conté una col·lecció de les tirades que complementen l'objecte 
  * de la classe.
  */

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import java.sql.*;


public class Partida {
	/**
	 * Determina un identificador de partida
	 */
	private int partidaID; 
	
	/**
	 * Determina l'estat de partida
	 */
	private boolean partida_acabada=false;
	
	/**
	 * Determina si una partida s'ha guanyat
	 */
	private boolean partida_guanyada=false;

	/**
	 * Determina la mida (en digits) del nombre aleatori
	 */
	private static byte SIZE;
	
	/**
	 * Quantitat de tirades per partida
	 */
	private static byte TIRADES;
	
	/**
	 * Nombre aleatori
	 */
	private byte[] aleatori;
	
	/**
	 * Dificultat de joc, si està a valor és nivell dificil
	 */
	private boolean dificultat=false; 
	
	/**
	 * Determina l'estat de partida, acabada o no
	 */
	private boolean partidaAcabada=false;
	
	/**
	 * Determina l'estat de partida, guanyada o no
	 */
	private boolean partidaGuanyada=false;
	
	/**
	 * Col·lecció de tirades
	 */
	private List<Tirada> llistaTirades=new ArrayList<Tirada>();
	
	/**
	 * Constructor buit
	*/
	public Partida(){}
	
	/**
	 * Constructor partida, genera un nombre aleatori de llargada SIZE
	 */

	public Partida(byte a, byte b){
		TIRADES = b;
		SIZE = a;
		aleatori=new byte [SIZE];
		partidaID=(BDMind.idPartida())+1;
		
		Random rnd=new Random();
		for (int i = 0; i < SIZE; i++) {
			aleatori[i]=(byte)(rnd.nextInt(10)+48);
		}
		///IMPRESIO DEL NUM ALEATORI TEMPORAL
		for (int i = 0; i < SIZE; i++) {
			System.out.print((char)aleatori[i]);
		}
	}
	/**
	 * Constructor partida a partir d'una partida guardada
	 */

	public Partida(int a, int b, int c,String d ,boolean e, boolean f, List<Tirada> g){
		partidaID = a;
		SIZE = (byte)b;
		TIRADES = (byte)c;
		aleatori = toBytes(farcit(d,b));
		partidaAcabada = e;
		partidaGuanyada = f;
		llistaTirades = g;	
	}
	
	/**
	 * Retorna la quantitat de tirades d'una partida 
	 */
	public byte getTirades(){return TIRADES;}
	
	/**
	 * Retorna la mida del aleatori d'una partida 
	 */
	public byte getSize(){return SIZE;}	
	
	/**
	 * Retorna l'estat d'una partida 
	 */
	public boolean getEstat(){return partidaAcabada;}
	
	/**
	 * Mètode per fer un set del estat d'una partida guanyada o acabada
	 */
	public void estatPartida(boolean estat, boolean guanyada){
		partidaAcabada=estat;
		partidaGuanyada=guanyada;
	}
	
	/**
	 * Mètode que converteix un nombre dins String en una taula de bytes. 
	 */
	protected static byte[] toBytes(String r){
		String[] temp = r.split("");
		byte[] bytes=new byte[SIZE];
		
		for (int i = 0; i < SIZE; i++) {
			bytes[i]=(byte)(Byte.parseByte(temp[i])+48);
		}
		return bytes;
	}
	
	/**
	 * Metode de farcit de zeros a l'esquerra
	 */
	protected static String farcit(String cad, int mida){
		for (cad.length(); cad.length() < mida ;) {
			cad=0+cad;	
		}
		return cad;
	}
	 
	/**
	 * Mètode per obtenir la mida del llistat de tirades
	 */
	public int getSizeColeccio(){return llistaTirades.size();}
	
	/**
	 * Mètode per afergir tirades a la col·lecció
	 */
	protected void afegirColeccio(Tirada t){llistaTirades.add(t);}
	
	/**
	 * Mètode per crear una nova tirada
	 */
	protected boolean novaTirada(String resposta, boolean newDificultat){
		dificultat=newDificultat;
		Tirada t = new Tirada(toBytes(resposta),aleatori,SIZE,this.partidaID);
		afegirColeccio(t);
		if(new String(resposta).equals(new String(aleatori))) {return true;}
		return false;
	}
	
	/**
	 * Mètode que prepara una partida per guardar-la
	 */
	protected String exportaPartida (){
		for (Iterator num=llistaTirades.iterator(); num.hasNext();) {
			BDMind.guardaTirada((Tirada)num.next());
		}
		return partidaID+","+SIZE+","+TIRADES+","+new String(aleatori)+","+partidaAcabada+","+partidaGuanyada;
	}
	
	/**
	 * Mètode de sobreescriptura (Object) per imprimir un objecte.
	 */ 
	public String toString(){
		String r="";
		for (Iterator num=llistaTirades.iterator(); num.hasNext();) {
			r=r+num.next()+"\n";
			if(dificultat) r=r.substring(0,r.length()-6)+"\n";
		}
		r="\nNombre\tPosició\tPosició\tTaula"+
		"\njugat\tincorrecte\tcorrecte\n"+r;
		return r;
	}
}





