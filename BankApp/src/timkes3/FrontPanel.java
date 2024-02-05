package timkes3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
@author Tim Åkesson, timkes-3
*/
public class FrontPanel extends Panel {
	
	
	
	
	
	
	
	private JTextArea infoTextArea;
	
	private JButton welcomeButton;
	
	
	
	private ImageIcon imageIcon;
	private JLabel imageLabel;
	
	
	FrontPanel()
	{
		components();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
    
	
	private void components() // initierar komponenter som finns i vår infoPanel och skapar en JTExtArea med scrollfu
	{
		
		
		
		
		componentsPanel= new JPanel(new BorderLayout());
		infoTextArea = new JTextArea();
		scrollPane = new JScrollPane(infoTextArea);
		welcomeButton= new JButton("Welcome! Please feel free to read the user manual below");
		
		welcomeButton.addActionListener(this);
		welcomeButton.setBackground(Color.black);
		welcomeButton.setForeground(Color.WHITE);
		welcomeButton.setEnabled(false);
		
		imageIcon = new ImageIcon("ImageFolder/bankroof.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(849, 160,  java.awt.Image.SCALE_SMOOTH); // scale it  
		imageIcon = new ImageIcon(newimg); 
		
		
		
		imageLabel = new JLabel();
		imageLabel.setIcon(imageIcon);
		
		
		infoTextArea.setText(informationText());
		infoTextArea.setEditable(false);
		
		scrollPane.setPreferredSize(new Dimension(200, 300));
		
		
		scrollPane.getVerticalScrollBar().addAdjustmentListener(adjustListener);
		
		scrollPane.getHorizontalScrollBar().addAdjustmentListener(adjustListener);
		
		buttonsPanel = new JPanel();
		
		
		buttonsPanel.add(welcomeButton);
		
		
		componentsPanel.add(imageLabel,BorderLayout.NORTH);
		
		
		componentsPanel.add(scrollPane,BorderLayout.SOUTH);
		componentsPanel.add(buttonsPanel,BorderLayout.CENTER);
		
		this.add(componentsPanel); // lägger till infoPanel i vår JPanel
		
	}
	
	private void actionListener()
	{
		
	}
	
	private String informationText() // Bruksanvisning
	{
		String text = "pNo: Personal number.\n"
				+ "\n---------------------------------------------------------------< Hello and welcome to this simple bank application >---------------------------------------------------------------"
				+ "\n\n"
				+ " This application is currently in beta stage v2, hence the function 'Save' and 'Read' is now operational.\n\n "
				+ "News: 1. Menu function Save and load. 2. Button function save transactions, in the account page\n" 
				+"\nUnder the tab 'Start' and 'Options' you will find all pages currently available: Customer & Account + a reference to this frontpage and the new functions save and load\n"
				+"\n Please read the bottom paragraph for understanding the correlation between pages Account and Customer."
				+ "\n\n <Customer>"
				+ "\n On the 'customer' page you can operate customers by creating a customer, see specific customer information, see every customer in the bank system and change current customer information except for pNo-numbers. "
				+ "\n You can also delete a customer(OBS! Once the customer is gone the information is gone for ever!). "
				+ "\n This page will provide you with 5 buttons for each action, an output field aswell as 3 information fields wich serves as user input where the final output filed gives feedback of your actions and provides data if you seek it."
				+ "\nActions of this page:\n\n Buttons: "
				+ "\n Create customer: Provide with name, lastname and customer pNo."
				+ "\n Get Customer: Privide with customer pNo and information will be given."
				+ "\n Change customer(name): Provide with customer pNo to change names."
				+"\n Delete Customer: Provide with customer pNo to delete the customer from bank inventory."
				+ "\n See all customers: Press and information about all customers will be given."
				+ "\n\nInformation fields:"
				+ " \nFirst name: Typ in customers first name"
				+" \nFirst name: Typ in customers last name"
				+" \nFirst name: Typ in customers pNo."
				
				+ "\n\n <Account>"
				+ "\n On the 'account' page you can manage accounts for a specific customer by providing the page with information specifik for each customer.\n  "
				+ "Since we don't expect our clients to remember anything you can simply press the button 'Informatin of bank inventory' for access to every customers pNo and every account the customer currently have. "
				+ "\n This page have a total of 8 buttons with different action such as: "
				+ "\n\nButtons:\nCreate savings account: Type in the customers pNo and create a new savings account."
				+ "\nCreate credit account: Type in the customers pNo and create a new credit account."
				+ "\nGet Account: Type in the customers pNo and account ID to see information about the account"
				+ "\nDeposit: Type in the customers pNo, account ID aswell as how much you want to deposit. If the deposit goes thru, the output field will give this information."
				+ "\nWithdraw: Type in the customers pNo, account ID aswell as how much you want to withdraw. If the withdraw goes thru, the output field will give this information. "
				+ "\nGet transactions: Type in the customers pNo, account ID. The output field will provide all the accounts history."
				+ "\nInformation of bank inventory: This function needs no inputs, press and information about every customer and there accounts will show."
				+ "\nClose account: Type in customer pNo and account Id to close the account(obs! once done, it's gone!)."
				+ "\n\nInformation fields:"
				+ "\n Amount: Type how much you want to deposit or withdraw."
				+ "\nAccount ID: Type the account you want to use."
				+ "\n pNo: Type in to whom the account beloings."
				+ "\n Output: Gives feedback to your acctions." 
				+ "\n\n <Account is an object in the customer object>"
				+ "\n There can't be an account if there is no customer!"
				+ " It's important to understand this hierarchy structure in order to understand this application."
				+ "\n In simple terms: the customer page gets used by authority (such as bank employee) to mange its customers. While the account page is managed by the individuals aswell as approved authority to ensure account actions occurs when needed and in a safe way."
				+ "<Save>\n"
				+ "Save the session in an file"
				+ "<Load>\n"
				+ "Load an already saved session from a file"
				+ "\n\n We appreciate feeback of any kind in order to grow to our full potential!"
				;
		return text;
	}
	
	

}

