package timkes3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JList;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
/*
@author Tim Åkesson, timkes-3
*/
// panel för Jmenu alternativet Customer
public class CustomerPanel extends Panel{
	
	
	
	private JButton SeeCustButton;
	private JButton createCustButton;
	private JButton getCustButton;
	private JButton changeCustButton;
	
	private JButton deleteCustButton;
	
	private JTextField firstNameField;
	private JTextField surrNameField;
	private JTextField pNoField;
	
	// konstruktor för panelen
	public CustomerPanel()
	{
	
		buttonsNField(); // lägger till komponenter som finns i övre delen av "pagen"
		resField(); // lägger till komponenter som finns i nedre delen av "pagen"
			
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		// variabler till diverse if-sats. 
		boolean state = false;
		String name = firstNameField.getText(); 
		String lName = surrNameField.getText(); // hämtar data från textfield
		String pNo = pNoField.getText();
		
		String text = new String();		// text till output-field
		String textArr[] = new String[1];
		
		String buttonText = e.getActionCommand(); // ger variabeln buttontext innehållet i knapen som används
		
		// nedan är if-satser som alla har en unik uppgift om en befintlig knapp används
		// i varje sats går metoden(finns i klassen "BankLogic") till varje knapp antingen igenom eller ej
		if(buttonText.equals("Create customer"))
		{
			
			
			state = bank.createCustomer(name,lName,pNo);
			if (state == true)
			{
				textArr[0] ="Customer added!";
				outputList.setListData(textArr);
			}
			else
			{
				textArr[0] ="Adding customer went wrong! Required do over! Provide us with a 10 number pNo aswell as a name and lastname with minumum of 1 letter!";
				outputList.setListData(textArr);
			}
		}
		
		if(buttonText.equals("Get customer"))
		{
			
			state = bank.checkForPNo(pNo);
			
			if((state == false))
			{
				
				text="No customer with that information! You only need to provide the correct pNo!";
				textArr[0] = text;
			}
			if(state == true)
			{
				
				textArr[0] = bank.getCustomer(pNo).toString();
				
			}
			outputList.setListData(textArr);
		}
		
		if(buttonText.equals("Change customer(name)"))
		{
			
			state = bank.changeCustomerName(name, lName, pNo);
			if (state == true)
			{
				textArr[0] ="Customer changes went through!";
				outputList.setListData(textArr);
			}
			else
			{
				textArr[0] ="Customer changes went wrong! Required do over! Submit the correct pNo and the name/names u would like to change!";
				outputList.setListData(textArr);
			}
				
		}
		
		if(buttonText.equals("Return to front page"))
		{
			
			this.setVisible(false);
		}
		
		if(buttonText.equals("See all customers"))
		{
			
			outputList.setListData(bank.getAllCustomers().toArray());
		}
		if(buttonText.equals("Delete Customer"))
		{
			
			state = bank.checkForPNo(pNo);
			
			if((state == false))
			{
				text="No customer with that information! ";
				textArr[0] = text;
			}
			if(state == true)
			{
				
				textArr[0] = bank.deleteCustomer(pNo).toString();
				
			}
			outputList.setListData(textArr);
		}
	}
	
	
	private void buttonsNField()
	{
		// initiera knappar och fält
		componentsPanel.setLayout(new BorderLayout());
		deleteButtonPanel.setLayout(new BorderLayout());
		
		final int panelWidth = 400;
		final int panelHeight = 250;
		
		
		createCustButton = new JButton("Create customer");
		getCustButton = new JButton("Get customer");
		changeCustButton = new JButton("Change customer(name)");
		
		SeeCustButton = new JButton("See all customers");
		deleteCustButton = new JButton("Delete Customer");
		deleteCustButton.setBackground(Color.RED);
		deleteCustButton.setForeground(Color.BLACK);
		
		
		componentsPanel.setPreferredSize(new Dimension(panelWidth,panelHeight));
		buttonsPanel.add(createCustButton);
		buttonsPanel.add(getCustButton);
		buttonsPanel.add(changeCustButton);
		
		
		deleteButtonPanel.add(deleteCustButton,BorderLayout.SOUTH);
		
		
		componentsPanel.add(deleteButtonPanel,BorderLayout.EAST);
		componentsPanel.add(buttonsPanel,BorderLayout.NORTH);
		
		final int width = 400;
		final int height = 50;
		
		firstNameField = new JTextField();
		surrNameField = new JTextField();
		pNoField = new JTextField();
		
		firstNameField .setPreferredSize(new Dimension(width,height));
		surrNameField .setPreferredSize(new Dimension(width,height));
		pNoField .setPreferredSize(new Dimension(width,height));
		
		firstNameField.setBorder(BorderFactory.createTitledBorder("First name:"));
		surrNameField.setBorder(BorderFactory.createTitledBorder("Last name:"));
		pNoField.setBorder(BorderFactory.createTitledBorder("PNo(10 numbers):"));
		
		textInputPanel.add(firstNameField);
		textInputPanel.add(surrNameField);
		textInputPanel.add(pNoField);
		
		componentsPanel .add(textInputPanel ,BorderLayout.CENTER);
		
		componentsPanel .add(SeeCustButton,BorderLayout.SOUTH);
		
		actionButtons();
		actionTextFields(); // ger alla knappar och fields lyssnare
		
		// lägger alla koponenter i huvudpanelen för denna "page"
		this.add(componentsPanel ,BorderLayout.NORTH);
		
	}
	
	
	
	private void actionButtons() // ger alla knappar lyssnare
	{
		SeeCustButton.addActionListener(this);
		createCustButton.addActionListener(this);
		getCustButton.addActionListener(this);
		changeCustButton.addActionListener(this);
		
		deleteCustButton.addActionListener(this);
	}
	
	private void actionTextFields() // ger alla fields lyssnare
	{
		firstNameField.addActionListener(this);
		surrNameField.addActionListener(this);
		pNoField.addActionListener(this);
	}
	
	
	private void resField() // initierar output fältet 
	{
		outputList = new JList();
		outputList.setLayoutOrientation(JList.VERTICAL);
		scrollPane = new JScrollPane(outputList);
		
		
		//----------Kod för att kunna skrolla upp/ner och sida till sida-----
			scrollPane.setPreferredSize(new Dimension(500, 200));

			scrollPane.getVerticalScrollBar().addAdjustmentListener(adjustListener);
			
			scrollPane.getHorizontalScrollBar().addAdjustmentListener(adjustListener);
		//----------------------------------------------------------------------------		
		
		outputList.setBorder(BorderFactory.createTitledBorder("Output"));
		this.add(scrollPane); // lägger till fältet längst ner på "huvudpagen"
		
	}

}

