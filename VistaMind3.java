/*
 * VistaMind3.java
 * 2018-05-22
 * 
 * Copyright 2018 Adri� Babot <daw1ababotllinares@iesjoanramis.org>
 */


import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.TitledBorder;

public class VistaMind3 {
	private JFrame frame = new JFrame("Ajuda - MasterMind v1.5.0");
	///t�tol
	private JLabel title = new JLabel(new ImageIcon("recursos/logo2.png"));	
	private JLabel textAjuda1 = new JLabel("<html><style type=\"text/css\"> p {text-align:justify;}</style><body width='350'><p>La finalitat del joc �s trobar el nombre amagat. Tradicionalment aquest joc es jugat amb colors, per� aqu� hi jugarem amb nombres. Per qu�? Perqu� podem.</p></body></html>");
	private JLabel textAjuda2 = new JLabel("<html><style type=\"text/css\"> p {text-align:justify;}</style><body width='350'><p>Abans d'iniciar una partida, pots modificar el nombre de d�gits que tindr� el nombre amagat. Tamb� podr�s modificar la quantitat de tirades que tindr�s a la partida. Aix� podr�s fer des del men� superior a la pantalla de partida, a la pestanya d'opcions, sempre <strong>ABANS</strong> de fer cap jugada de la partida, una vegada hagis fet una jugada, ja no podr�s modificar aquests par�metres. <br/>Disposes tamb� de dos nivells de joc; en nivell normal, que mostra una taula d'ajuda per saber on estan posicionats els d�gits correctes i correctes a mala posici� respecte al nombre amagat, i el nivell avan�at, que no mostrar� aquesta taula. El nivell de joc el podr�s canviar durant tota la partida, les vegades que vulguis. Podr�s modificar el nivell des del men� superior a la pantalla de partida, a la pestanya d'opcions.</p></body></html>");
	private JLabel textAjuda3 = new JLabel("<html><style type=\"text/css\"> p {text-align:justify;}</style><body width='350'><p>Des de la pantalla inicial podr�s seleccionar l'opci� que m�s convingui, iniciar una nova partida des de zero o carregar una partida desada anterior. Jugar una partida desada implica seguir jugant des del punt exacta on es va desar.</p></body></html>");
	private JLabel textAjuda4 = new JLabel("<html><style type=\"text/css\"> p {text-align:justify;}</style><body width='350'><p>Introdueix un nombre amb la quantitat de d�gits triada (per defecte s�n 5 d�gits), clica Comprova.<br/> El joc et mostrar� una taula amb el nombre jugat, els d�gits ben posicionats, els d�gits mal posicionats i depenent del nivell tamb� mostrar� la taula d'ajuda.\nPodr�s repetir aquesta acci� tantes vedades com tirades hagis triat (per defecte s�n 10 tirades). Si encertes el nombre amagat, el joc t'avisar�. Si esgotes la quantitat de tirades sense encertar-lo, haur�s perdut.\nPer iniciar una nova partida haur�s de fer clic al men� superior a la pestanya d'arxiu. Des d'aquest mateix men� podr�s tornar a la finestra inicial.<br/>Si no pots acabar la partida, pots desar-la des del men� arxiu per poder continuar amb aquesta m�s tard.</p></body></html>");
	
	/**Constructor per la interfice g�fica ajuda */
	public VistaMind3(){
		frame.setSize(450,450);
		ImageIcon img = new ImageIcon("recursos/letter.png");
		frame.setIconImage(img.getImage());

		JPanel panel = new JPanel();
        JScrollPane scrPane = new JScrollPane(panel);
        frame.getContentPane().add(scrPane);
        panel.setLayout(new GridBagLayout());
		
		panel.setBackground(Color.WHITE);
		
		textAjuda1.setFont(new Font("Arial",Font.PLAIN,12));
		TitledBorder  border1 = new TitledBorder("Mastermind");
		textAjuda1.setBorder(border1);
		
		textAjuda2.setFont(new Font("Arial",Font.PLAIN,12));
		TitledBorder border2 = new TitledBorder("Variables");
		textAjuda2.setBorder(border2);
		
		textAjuda3.setFont(new Font("Arial",Font.PLAIN,12));
		TitledBorder border3 = new TitledBorder("Partida nova o obra una partida");
		textAjuda3.setBorder(border3);
		
		textAjuda4.setFont(new Font("Arial",Font.PLAIN,12));
		TitledBorder border4 = new TitledBorder("Mec�nica de joc");
		textAjuda4.setBorder(border4);
		
		panel.add(title,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(10,0,10,0),0,0));
		panel.add(textAjuda1,new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));
		panel.add(textAjuda2,new GridBagConstraints(0,3,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));
		panel.add(textAjuda3,new GridBagConstraints(0,4,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));
		panel.add(textAjuda4,new GridBagConstraints(0,5,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,15,0),0,0));

		frame.addWindowListener(new BWinListener());
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);	
	}

	private class BWinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			frame.setVisible(false);
		}
	}	
	public static void main (String[] args) {
		new VistaMind3();
	}
}

