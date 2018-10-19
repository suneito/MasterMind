/*
 * VistaMind4.java
 * 2018-06-14
 * 
 * Copyright 2018 Adrià Babot <daw1ababotllinares@iesjoanramis.org>
 */

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

public class VistaMind4 {
	
	private JFrame frame = new JFrame("Partides - MasterMind v1.5.0");
	///títol
	private JLabel title = new JLabel(new ImageIcon("recursos/logo2.png"));	
	private JLabel tag1 = new JLabel("Introdueix el ID de la partida a obrir:");	
	
	///buttons
	private JButton buttonObra = new JButton("Obrir");
	
	///display
	private JTextField display = new JTextField("");
	
	///textaerea
	private JTextArea partides = new JTextArea("");
	
	///menu butons
	private JMenu arxiuMenu = new JMenu("Arxiu");
    private JMenu helpMenu = new JMenu("Ajuda");
	
	/**Constructor per la interfice gràfica partides */
	public VistaMind4(){
		frame.setSize(450,450);
		ImageIcon img = new ImageIcon("recursos/letter.png");
		frame.setIconImage(img.getImage());

		JPanel panell = new JPanel();
        JScrollPane scrPane = new JScrollPane(panell);
        frame.getContentPane().add(scrPane);
        panell.setLayout(new GridBagLayout());
		
		panell.setBackground(Color.WHITE);
		///button color
		buttonObra.setBackground(new Color(169,192,255));
		
		///menu
		// Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
        UIManager.put("MenuItem.background", Color.WHITE);
         
        // Add the menubar to the frame
        frame.setJMenuBar(menuBar);
        menuBar.setBackground(new Color(169,192,255));
         
        // Define and add two drop down menu to the menubar
        
        menuBar.add(arxiuMenu);
        menuBar.add(helpMenu);
         
        // Create and add simple menu item to one of the drop down menu
        JMenuItem inici = new JMenuItem("Inici");
        JMenuItem exitAction = new JMenuItem("Sortir");
        JMenuItem help = new JMenuItem("Ajuda");
        JMenuItem credits = new JMenuItem("Quant a");
        
        arxiuMenu.add(inici);
        arxiuMenu.addSeparator();
        arxiuMenu.add(exitAction);
        helpMenu.add(help);
        helpMenu.addSeparator();
        helpMenu.add(credits);
        
		partides.setText(BDMind.llistatPartides());
		
		panell.add(title,new GridBagConstraints(0,0,4,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(10,0,10,0),0,0));
		panell.add(tag1,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(10,5,10,0),0,0));
		panell.add(display,new GridBagConstraints(0,2,2,1,0.9,0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,5,0,0),0,0));
		panell.add(buttonObra,new GridBagConstraints(3,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,5,0,5),0,0));
		panell.add(partides,new GridBagConstraints(0,3,4,10,1.0,0.5,GridBagConstraints.NORTH,GridBagConstraints.NONE,new Insets(15,5,5,5),0,0));
		
		frame.addWindowListener(new BWinListener());
		credits.addActionListener(new creditsActionListener());
		
		/**Obra la partida amb id seleccionat*/
		buttonObra.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (BDMind.partidaExistent(Integer.parseInt(display.getText()))) {
					frame.setVisible(false);
					new VistaMind2(Integer.parseInt(display.getText()));
				}else{
					JOptionPane.showMessageDialog(null,"ID errònia. Torna a provar-ho","Quant a MasterMind",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		/**adaptar funcionament del enter per enviar num*/
		display.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){buttonObra.doClick();}
			}
			public void keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){}
		});
		
		/**Tornar a la pantalla d'inici'*/
		inici.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(false);
				new VistaMind1();//inici
			}
		});
		
		/**Obrir la finestra d'ajuda'*/
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new VistaMind3();//ajuda
			}
		});
		
		/**Sortida de l'app*/
		exitAction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		

		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);	
	}
	
	/**Mostra els credits*/
	private class creditsActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null,"<html><h2>Copyright(c) 2018 Adrià Babot</h2><p {text-align:center;}>daw1ababotllinares@iesjoanramis.org<br/>Versió GUI 1.5.0</p></html>", "Quant a MasterMind",JOptionPane.INFORMATION_MESSAGE);
		}
	}	

	private class BWinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			frame.setVisible(false);
		}
	}	
	public static void main (String[] args) {
		new VistaMind4();
	}
}

