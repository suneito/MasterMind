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

public class VistaMind2 {
	///frame
	private JFrame frame = new JFrame("Partida - MasterMind v1.5.0");
	
	///panell
	private JPanel panell = (JPanel)frame.getContentPane();
	
	///title
	private JLabel title = new JLabel(new ImageIcon("recursos/logo2.png"));
	
	///display
	private JTextField display = new JTextField("");
	
	///textaerea
	private JTextArea taula = new JTextArea("");
	
	///buttons
	private JButton buttonComp = new JButton("Comprova");
	private JButton buttonErase = new JButton("Esborra");
	
	///menu butons
	private JMenu arxiuMenu = new JMenu("Arxiu");
    private JMenu opcionsMenu = new JMenu("Opcions");
    private JMenu helpMenu = new JMenu("Ajuda");
    private JRadioButtonMenuItem radioAction1 = new JRadioButtonMenuItem("Nivell normal",true);
    private JRadioButtonMenuItem radioAction2 = new JRadioButtonMenuItem("Nivell avançat");
    
    /// Create and add simple menu item to one of the drop down menu
	private JMenuItem inici = new JMenuItem("Inici");
	private JMenuItem newGame = new JMenuItem("Nova partida");
	private JMenuItem openGame = new JMenuItem("Obrir partida");
	private JMenuItem saveGame = new JMenuItem("Guardar partida");
	private JMenuItem exitAction = new JMenuItem("Sortir");
	private JMenuItem sizeNumber = new JMenuItem("Digits del nombre");
	private JMenuItem rounds = new JMenuItem("Tirades");
	private JMenuItem help = new JMenuItem("Ajuda");
	private JMenuItem credits = new JMenuItem("Quant a");
	
	///atributs
	private Partida p1=null;
	private int counter=-1;
	private byte tirades=10;
	private byte size=5;
	
	/**Constructor per la interfice gàfica general del joc */
	public VistaMind2(int carrega){
		frame.setSize(450,450);
		ImageIcon img = new ImageIcon("recursos/letter.png");
		frame.setIconImage(img.getImage());
		panell.setLayout(new GridBagLayout());
		panell.setBackground(Color.WHITE);
	
		///menu
		// Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
         
        // Add the menubar to the frame
        frame.setJMenuBar(menuBar);
        menuBar.setBackground(new Color(169,192,255));
         
        // Define and add two drop down menu to the menubar
        menuBar.add(arxiuMenu);
        menuBar.add(opcionsMenu);
        menuBar.add(helpMenu);
         
        // Create a ButtonGroup and add both radio Button to it. Only one radio
        // button in a ButtonGroup can be selected at a time.
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioAction1);
        bg.add(radioAction2);
        arxiuMenu.add(inici);
        arxiuMenu.add(newGame);
        arxiuMenu.add(openGame);
        arxiuMenu.add(saveGame);
        arxiuMenu.addSeparator();
        arxiuMenu.add(exitAction);
        opcionsMenu.add(rounds);
        opcionsMenu.add(sizeNumber);
        opcionsMenu.addSeparator();
        opcionsMenu.add(radioAction1);
        opcionsMenu.add(radioAction2);
        helpMenu.add(help);
        helpMenu.addSeparator();
        helpMenu.add(credits);
        
        ///perzonalitzacio del menus
		UIManager.put("OptionPane.background", Color.WHITE);//canvia el color del fons dels JOption pane ("frame")
		UIManager.getLookAndFeelDefaults().put("Panel.background", Color.WHITE);//canvia el color del fons dels JOption pane ("panell")
		UIManager.put("MenuItem.background", Color.WHITE);//canvia el color de fons de la llista deplegable dels menus
		///estat del menu inicial
		saveGame.setEnabled(false);///no pots guardar una partida abans de fer cap tirada
		
		///tags
		JLabel tagNum = new JLabel("Nombre jugat:");
		tagNum.setFont(new Font("Arial",Font.BOLD,12));
		
		///boto
		buttonComp.setBackground(new Color(169,192,255));
		buttonErase.setBackground(new Color(169,192,255));
		
		///textaerea
		taula.setEditable(false);
		
		///display
		display.setHorizontalAlignment(SwingConstants.LEFT);
		display.setEditable(true);
		display.setBackground(Color.WHITE);
		
		///panel adds
		panell.add(title,new GridBagConstraints(0,0,5,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(10,0,10,0),0,0));
		panell.add(tagNum,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,5,5,0),0,0));
		panell.add(display,new GridBagConstraints(0,2,3,1,0.9,0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,5,0,0),0,0));
		panell.add(buttonComp,new GridBagConstraints(3,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,5,0,5),0,0));
		panell.add(buttonErase,new GridBagConstraints(4,2,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,5,0,5),0,0));
		panell.add(taula,new GridBagConstraints(0,3,5,10,1.0,0.5,GridBagConstraints.NORTH,GridBagConstraints.NONE,new Insets(0,5,5,5),0,0));
		
		if (carrega>0) {
			p1=BDMind.obtePartida(carrega);
			tirades=p1.getTirades();
			size=p1.getSize();
			counter=p1.getSizeColeccio();
			
			if (p1.getEstat()) {
				buttonComp.setEnabled(false);///disable el botó
				saveGame.setEnabled(false);///desactiva el boto de guardar partida
			}else{
				BDMind.borraPartida(carrega);
			}
			taula.setText(p1+"");
			
		}

		///listeners
		buttonComp.addActionListener(new ButtonActionListener());
		openGame.addActionListener(new ButtonPartidesActionListener());
		rounds.addActionListener(new roundsActionListener());
		sizeNumber.addActionListener(new sizeNumberActionListener());
		credits.addActionListener(new creditsActionListener());
		
		///listeners classes anònimes
		/**crea una finestra nova per a una partida nova*/
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(false);
				new VistaMind2(-1);
			}
		});
		
		/**adaptar funcionament del enter per enviar num*/
		display.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){buttonComp.doClick();}
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
		
		/**Guarda la partida*/
		saveGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				BDMind.guardaPartida(p1);
				JOptionPane.showMessageDialog(null,"S'ha guardat la partida", "Partida guardada",JOptionPane.INFORMATION_MESSAGE);
				frame.setVisible(false);
				new VistaMind2(-1);
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
		
		frame.addWindowListener(new BWinListener());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);	
	}
	
	private class ButtonActionListener implements ActionListener{
	
		public void actionPerformed(ActionEvent e){
			boolean guanyada=false, dificultat=false;
			String textDispy="";
			
			if(counter==-1) { //crea una partida nova
				p1=new Partida(size,tirades);
				++counter;
			}
			
			if (radioAction2.isSelected()) {
				dificultat=true;
			}

			try{
				textDispy=display.getText();
				if(textDispy.length()>size) throw new ArrayIndexOutOfBoundsException();/// nombre introduit massa llarg
				
				guanyada=p1.novaTirada(textDispy,dificultat);
				display.setText(null);
				
				taula.setText(p1+"");
				if(guanyada) {
					saveGame.setEnabled(false);///desactiva el boto de guardar partida
					p1.estatPartida(true,true);///set partida acabada,guanyada
					BDMind.guardaPartida(p1);///guarda a la base de dades la partida acabada guanyada
					JOptionPane.showMessageDialog(null,"<html><h2{text-align:center;}>Enorabona!</h2><p>Has trobat el nombre amagat!</p></html>", "Victòria!",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("recursos/confetti.gif"));
					buttonComp.setEnabled(false);///disable el botó
				}
				++counter;
				
				if(counter>=tirades&&!guanyada){
					saveGame.setEnabled(false);///desactiva el boto de guardar partida
					p1.estatPartida(true,false);///set partida acabada, perduda
					BDMind.guardaPartida(p1);///guarda a la base de dades la partida acabada perduda
					JOptionPane.showMessageDialog(null,"<html><h2{text-align:center;}>Joc acabat</h2><p>No has trobat el nombre amagat.</p></html>", "Llàstima!",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("recursos/cat.gif"));
					buttonComp.setEnabled(false);///disable el botó
				}
				
				if(counter>0){
					rounds.setEnabled(false);///disable el botó menú opcions-tirades
					sizeNumber.setEnabled(false);///disable el botó menú opcions-tirades
					saveGame.setEnabled(true);///activa el boto de guardar partida
				}
			}catch(NumberFormatException ne){
				JOptionPane.showMessageDialog(null,"Has insertat \"algo\" incorrecte, introdueix un nombre vàlid", "Alerta",JOptionPane.WARNING_MESSAGE);
			}catch(ArrayIndexOutOfBoundsException ofb){
				JOptionPane.showMessageDialog(null,"Has insertat un nombre de llargada incorrecte, \ninsereix un nombre de llargada "+size+".", "Alerta",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private class ButtonPartidesActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			frame.setVisible(false);
			new VistaMind4();
		}
	}
	/**Selecciona la quantitat de tirades per la partida*/
	private class roundsActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object[] possibleValues = {2,3,4,5,6,7,8,9,10};
			try{
				Object newValueOfRounds = JOptionPane.showInputDialog(null,"Tria el nombre de tirades per la partida", "Nombre de tirades",JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
				tirades=Byte.parseByte(newValueOfRounds+"");
			}catch(NumberFormatException ne){}
		}
	}
	/**Selecciona la quantitat de digits del nombre amagat*/
	private class sizeNumberActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object[] possibleValues = {3,4,5,6,7,8};
			try{
				Object newValueOfSize = JOptionPane.showInputDialog(null,"Tria el nombre de digits del nombre amagat", "Quantitat de dígits",JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
				size=Byte.parseByte(newValueOfSize+"");
			}catch(NumberFormatException ne){}
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
			int result = JOptionPane.showConfirmDialog(frame,"Estas segura que vols sortir?", "Confirmació de sortida",JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION){
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}else {
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
	}
		public static void main (String[] args) {
		new VistaMind2(-1);
	}	
}

