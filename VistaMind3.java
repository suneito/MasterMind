/*
 * VistaMind3.java
 * 2018-05-22
 * 
 * Copyright 2018 Adrià Babot <daw1ababotllinares@iesjoanramis.org>
 */


import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.TitledBorder;

public class VistaMind3 {
	private JFrame frame = new JFrame("Ajuda - MasterMind v1.5.0");
	///títol
	private JLabel title = new JLabel(new ImageIcon("recursos/logo2.png"));	
	private JLabel textAjuda1 = new JLabel("<html><style type=\"text/css\"> p {text-align:justify;}</style><body width='350'><p>La finalitat del joc és trobar el nombre amagat. Tradicionalment aquest joc es jugat amb colors, però aquí hi jugarem amb nombres. Per què? Perquè podem.</p></body></html>");
	private JLabel textAjuda2 = new JLabel("<html><style type=\"text/css\"> p {text-align:justify;}</style><body width='350'><p>Abans d'iniciar una partida, pots modificar el nombre de dígits que tindrà el nombre amagat. També podràs modificar la quantitat de tirades que tindràs a la partida. Això podràs fer des del menú superior a la pantalla de partida, a la pestanya d'opcions, sempre <strong>ABANS</strong> de fer cap jugada de la partida, una vegada hagis fet una jugada, ja no podràs modificar aquests paràmetres. <br/>Disposes també de dos nivells de joc; en nivell normal, que mostra una taula d'ajuda per saber on estan posicionats els dígits correctes i correctes a mala posició respecte al nombre amagat, i el nivell avançat, que no mostrarà aquesta taula. El nivell de joc el podràs canviar durant tota la partida, les vegades que vulguis. Podràs modificar el nivell des del menú superior a la pantalla de partida, a la pestanya d'opcions.</p></body></html>");
	private JLabel textAjuda3 = new JLabel("<html><style type=\"text/css\"> p {text-align:justify;}</style><body width='350'><p>Des de la pantalla inicial podràs seleccionar l'opció que més convingui, iniciar una nova partida des de zero o carregar una partida desada anterior. Jugar una partida desada implica seguir jugant des del punt exacta on es va desar.</p></body></html>");
	private JLabel textAjuda4 = new JLabel("<html><style type=\"text/css\"> p {text-align:justify;}</style><body width='350'><p>Introdueix un nombre amb la quantitat de dígits triada (per defecte són 5 dígits), clica Comprova.<br/> El joc et mostrarà una taula amb el nombre jugat, els dígits ben posicionats, els dígits mal posicionats i depenent del nivell també mostrarà la taula d'ajuda.\nPodràs repetir aquesta acció tantes vedades com tirades hagis triat (per defecte són 10 tirades). Si encertes el nombre amagat, el joc t'avisarà. Si esgotes la quantitat de tirades sense encertar-lo, hauràs perdut.\nPer iniciar una nova partida hauràs de fer clic al menú superior a la pestanya d'arxiu. Des d'aquest mateix menú podràs tornar a la finestra inicial.<br/>Si no pots acabar la partida, pots desar-la des del menú arxiu per poder continuar amb aquesta més tard.</p></body></html>");
	
	/**Constructor per la interfice gàfica ajuda */
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
		TitledBorder border4 = new TitledBorder("Mecànica de joc");
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

