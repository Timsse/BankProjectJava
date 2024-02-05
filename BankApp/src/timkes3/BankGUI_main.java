package timkes3;


import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/*
@author Tim Åkesson, timkes-3
Huvudklassen för vår GUI vilket innehåller en Jframe och JMenu och referenser från Components-klassen
*/
public class BankGUI_main extends JFrame implements ActionListener {
	
	// alla instansvariabler + referens till alla klasser som är lägre ner i ordningen. detta är huvudklassen
	private BankGUI_Components accPanel;
	private BankGUI_Components customerPanel;
	private BankGUI_Components FrontPanel;
	
	@SuppressWarnings("used in actionPerformed")
	private BankGUI_Components saveNLoad ;
	
	private JMenuItem acc;
	private JMenuItem customer;
	private JMenuItem frontPage;
	
	private JMenuItem saveBank;
	
	private JMenuItem loadBank;
	
	// konstruktor för klassen
	public BankGUI_main()
	{
		
		
		
		myFrame();
		
		bankMenu();
		
		
	
		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		 new BankGUI_main();
	}
	
	
// i denna klass finns vår JFrame vilket innehåller vår Jmenus alla möjliga val här nedan är för Jmenus olika knappval

public void actionPerformed(ActionEvent e) {
		
		String text = e.getActionCommand();
		
		//---------------------------------------------------------------------------------------------------------------------------
		if(text.equals("Customer"))       // väljer användaren fliken customer blir endast den panelen synlig
		{	
			pageFalse();
			customerPanel.setVisible(true);
		}
		
		//---------------------------------------------------------------------------------------------------------------------------
		if(text.equals("Account")) 	// väljer användaren fliken account blir endast den panelen synlig
		{	
			pageFalse();
			accPanel.setVisible(true);
		//---------------------------------------------------------------------------------------------------------------------------
		}
		if(text.equals("Frontpage"))	// väljer användaren fliken frontpage blir endast den panelen synlig
		{	
			pageFalse();
			FrontPanel.setVisible(true);
		}
		//---------------------------------------------------------------------------------------------------------------------------
		if(text.equals("Save Bank"))
		{
			saveNLoad = new MenuGUILogic("Save Bank");
			
		}
		
		//---------------------------------------------------------------------------------------------------------------------------
		if(text.equals("Load Bank"))
		{
			saveNLoad = new MenuGUILogic("Load Bank");
		}
	}
//---------------------------------------------------------------------------------------------------------------------------
	private void pageFalse()
	{
		customerPanel.setVisible(false);
		FrontPanel.setVisible(false);
		accPanel.setVisible(false);
		
	}
	//---------------------------------------------------------------------------------------------------------------------------
	private void myFrame() // all kod som utformar JFrame + här skapas initieras alla komponenter som är direkt i JFrame
	{
		this.setTitle("Bank Logic");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900,700);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		FrontPanel = new FrontPanel();
		customerPanel = new CustomerPanel();
		accPanel = new AccountPanel();
		
		pageFalse();
		
		FrontPanel.setVisible(true);
		
		
		
		
		this.add(customerPanel);
		this.add(accPanel);
		this.add(FrontPanel);
		
		
		
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	private void bankMenu() // initierar komponenter som vår JMenu besitter
	{
		JMenuBar bankMenu = new JMenuBar();
		
		JMenu accMenu = new JMenu("Start");
		JMenu saveNLoadMenu = new JMenu("Options");	// skapar 3 olika tabbar
		
		
		
		
		bankMenu.add(accMenu);
		bankMenu.add(saveNLoadMenu);
		//bankMenu.add(readMenu);
		
		acc = new JMenuItem("Account");
		customer = new JMenuItem("Customer");  // ger Start tabben 3 alternativ för användaren att välja mellan.
		frontPage = new JMenuItem("Frontpage");
		
		saveBank = new JMenuItem("Save Bank");
		
		loadBank = new JMenuItem("Load Bank");
		
		actionListenersJMenu();
		
		saveNLoadMenu.add(saveBank);
		
		saveNLoadMenu.add(loadBank);
		
		accMenu.add(frontPage);
		accMenu.add(customer);
		accMenu.add(acc);
		
		
		this.add(bankMenu);
		this.setJMenuBar(bankMenu);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	private void actionListenersJMenu() // ger komponenterna actionListeners
	{
		customer.addActionListener(this);
		acc.addActionListener(this);
		frontPage.addActionListener(this);
		saveBank.addActionListener(this);
		
		loadBank.addActionListener(this);
	}
	
	
	
	
	
	
	
	
	


	

	
}
