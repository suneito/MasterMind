/*
 * VistaMind.java
 * 2018-05-13
 * 
 * Copyright 2018 Adrià Babot <daw1ababotllinares@iesjoanramis.org>
 * 
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

public class VistaMind1 {
	private JFrame frame = new JFrame("MasterMind v1.5.0");
	private JPanel panel0 = (JPanel)frame.getContentPane();
	///títol
	private JLabel title = new JLabel(new ImageIcon("recursos/logo.png"));
	///buttons
	private JButton buttonPartides = new JButton("Obrir partida");
	private JButton buttonStart = new JButton("Partida nova ");
	///menu butons
	private JMenu arxiuMenu = new JMenu("Arxiu");
    private JMenu helpMenu = new JMenu("Ajuda");
	
	
	/**Constructor per la interfice gàfica inicial */
	public VistaMind1(){
		frame.setSize(600,400);
		ImageIcon img = new ImageIcon("recursos/letter.png");
		frame.setIconImage(img.getImage());
		panel0.setLayout(new GridBagLayout());
		panel0.setBackground(Color.WHITE);
	
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
        JMenuItem newGame = new JMenuItem("Nova partida");
        JMenuItem openGame = new JMenuItem("Obrir partida");
        JMenuItem exitAction = new JMenuItem("Sortir");
        JMenuItem help = new JMenuItem("Ajuda");
        JMenuItem credits = new JMenuItem("Quant a");
        
        arxiuMenu.add(newGame);
        arxiuMenu.add(openGame);
        arxiuMenu.addSeparator();
        arxiuMenu.add(exitAction);
        helpMenu.add(help);
        helpMenu.addSeparator();
        helpMenu.add(credits);
		
		UIManager.put("OptionPane.background", Color.WHITE);//canvia el color del fons dels JOption pane ("frame")
		UIManager.getLookAndFeelDefaults().put("Panel.background", Color.WHITE);//canvia el color del fons dels JOption pane ("panell")
		
		///button color
		buttonPartides.setBackground(new Color(169,192,255));
		buttonStart.setBackground(new Color(169,192,255));

		panel0.add(title,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));
		panel0.add(buttonStart,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,5,0,5),0,0));
		panel0.add(buttonPartides,new GridBagConstraints(0,2,1,1,1.0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(12,5,5,5),0,0));
		///crea la bd si no existeix
		BDMind.creaBD();
		
		///listeners
		buttonPartides.addActionListener(new ButtonPartidesActionListener());
		openGame.addActionListener(new ButtonPartidesActionListener());
		buttonStart.addActionListener(new partidaNovaActionListener());
		newGame.addActionListener(new partidaNovaActionListener());
		credits.addActionListener(new creditsActionListener());

		///listeners classes anònimes
		exitAction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new VistaMind3();
			}
		});
		frame.addWindowListener(new BWinListener());
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);	
	}
	private class partidaNovaActionListener implements ActionListener{	
		public void actionPerformed(ActionEvent e){
			frame.setVisible(false);
			new VistaMind2(-1);
		}
	}
	private class ButtonPartidesActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			frame.setVisible(false);
			new VistaMind4();
		}
	}
	
	/**Mostra els credits*/
	private class creditsActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null,"<html><h2>Copyright(c) 2018 Adrià Babot</h2><p {text-align:center;}>daw1ababotllinares@iesjoanramis.org<br/>Versió GUI 1.5.0</p></html>", "Quant a MasterMind",JOptionPane.INFORMATION_MESSAGE);
		}
	}	
	
	private class BWinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.exit(0);
		}
	}	
	public static void main (String[] args) {
		new VistaMind1();
	}
}

