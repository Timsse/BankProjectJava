package timkes3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/*
@author Tim Åkesson, timkes-3
*/
//panel för Jmenu alternativet account
public class AccountPanel extends Panel {
	
	
	
	private JButton CreateSavingsButton;
	private JButton createCreditButton;
	private JButton getAccButton;
	private JButton depositButton;
	private JButton withdrawButton;
	private JButton closeAccButton;
	private JButton getTransactButton;
	private JButton saveTransactButton;
	
	private JButton accountsNCustomersButton;	
	private JTextField pNoField;
	private JTextField accIdField;
	private JTextField amountField;
	
	
	//konstruktor för panelen
	public AccountPanel()
	{
		
		
		
		buttonsNField(); // lägger till komponenter som finns i övre delen av "pagen"
		resField(); // lägger till komponenter som finns i nedre delen av "pagen"
		
		
	}
	public void actionPerformed(ActionEvent e) {
		
		boolean state = false;
		
		
		String text = new String();		// text till output-field
		String textArr[] = new String[1];
		
		String pNo = pNoField.getText();
		
		
		String buttonText = e.getActionCommand(); // ger variabeln buttontext innehållet i knapen som används
		
		// nedan är if-satser som alla har en unik uppgift om en befintlig knapp används
		// i varje sats går metoden(finns i klassen "BankLogic") till varje knapp antingen igenom eller ej
		//----------------------------------------------------------------------------------------------
		if(buttonText.equals("Create Savings"))
		{
			
			
			int check;
		
			check = bank.createSavingsAccount(pNo);
			if (check == -1)
			{
				
				textArr[0]=" Adding account went wrong! pNo might not exsist in our system!";
				outputList.setListData(textArr);
			}
			else
			{
				
				String checkString = Integer.toString(check);
				textArr[0]= "Account createt successfully! ID:"+ checkString;
				outputList.setListData(textArr);
			}
		}
		//----------------------------------------------------------------------------------------------
		if(buttonText.equals("Create Credits"))
		{
			
			int check;
		
			check = bank.createCreditAccount(pNo);
			
			if (check == -1)
			{
				
				textArr[0]=" Adding account went wrong! pNo might not exsist in our system!";
				
			}
			else
			{
				
				String checkString = Integer.toString(check);
				textArr[0]= "Account createt successfully! ID:"+ checkString;
				
			}
			outputList.setListData(textArr);
		}
		//----------------------------------------------------------------------------------------------
		if(buttonText.equals("Get Account"))
		
		
		{
			int acc;
			try
			{
			 acc = Integer.parseInt(accIdField.getText());
			}
			catch(RuntimeException e2)
			{
				acc = 0;
			}
			state = bank.checkForPNo(pNo);
			
			if(state == true  )
			{
				
				
				if (bank.getAccount(pNo, acc) != null)
				{
					textArr[0] = bank.getAccount(pNo, acc);
				}
				else
				{
					textArr[0] = " Get accounts went wrong! pNo might not match witt accID !";
				}
				
			}
			else
			{
				textArr[0] = " Get accounts went wrong! pNo might not exsist in our system!";
				
			}
			outputList.setListData(textArr);
		}
		//----------------------------------------------------------------------------------------------
		if(buttonText.equals("Deposit"))
		{
			int amount;
			int acc;
			
		
			try
			{
				amount = Integer.parseInt(amountField.getText());
				acc = Integer.parseInt(accIdField.getText());
			}
			catch(RuntimeException e2)
			{
				amount= -1;
				acc=1;
			}
				
				
				
			state = bank.deposit(pNo, acc, amount);
			if(state==true)
			{
				textArr[0] = "Deposit went through!";
			}
			else
			{
				textArr[0] = "No deposit went through! Check if pNo and ID is correct and amount to be more than zero.";
			}
			
			outputList.setListData(textArr);
		}
		//----------------------------------------------------------------------------------------------
		if(buttonText.equals("Withdraw"))
		{
			
			int amount = Integer.parseInt(amountField.getText());
			int acc = Integer.parseInt(accIdField.getText());
			state = bank.withdraw(pNo, acc, amount);
			if(state==true)
			{
				textArr[0] = "Withdraw went through!";
			}
			else if (state == false)
			{
				
				textArr[0] = "No withdraw went through! Check pNo, ID and balance. Remember for savingsaccount withdraw is only free for the first use!\n Latest transactions: "+bank.getTransactions(pNo, acc);
			}
			
			outputList.setListData(textArr);
		}
		//----------------------------------------------------------------------------------------------
		if(buttonText.equals("Close account"))
		{
			
			int acc = Integer.parseInt(accIdField.getText());
			if(bank.checkForPNo(pNo)== false)
			{
				textArr[0]= "pNo does not exist in our system";
				
			}
			else if (bank.getAccount(pNo, acc)== null)
			{
				textArr[0]= "Account is already gone! Or never existed ";
			}
			else
			{
				textArr[0] = bank.closeAccount(pNo, acc);
			}
			outputList.setListData(textArr);
		}
		//----------------------------------------------------------------------------------------------
		if(buttonText.equals("Get transactions"))
		{
			ArrayList array = new ArrayList();
			
			int acc = Integer.parseInt(accIdField.getText());
			
			if(bank.getTransactions(pNo, acc)==null)
			{
				textArr[0] = "There is no information to give! please try again with other information ";
				outputList.setListData(textArr);
			}
			else
			{
				array = bank.getTransactions(pNo, acc);
				outputList.setListData(array.toArray());
			}
		}
		//----------------------------------------------------------------------------------------------
		if(buttonText.equals("Information of bank inventory"))
		{
			outputList.setListData(bank.getCustomersAndAccounts().toArray());
		}
		//----------------------------------------------------------------------------------------------
		if(buttonText.equals("Save transactions"))
		{
			
			int acc = Integer.parseInt(accIdField.getText());
			int pass =0;
			try
			{
				JFileChooser startFile = new JFileChooser("Timkes3_files");
				int respons = startFile.showSaveDialog(null); // välj fil som ska öppnas
				if (respons == JFileChooser.APPROVE_OPTION)
				{
					File file = new File(startFile.getSelectedFile().getAbsolutePath());
					
					pass= bank.saveTransactions(file, pNo, acc);
					if(pass == 1)
					{	
						JOptionPane.showMessageDialog(null, "sucess");
					}
					else
					{
						textArr[0] = "Try with different information ";
						outputList.setListData(textArr);
					}
				}
				
			}
			catch(RuntimeException e2)
			{
				JOptionPane.showMessageDialog(null, "Something went worng!");
			}
			
		}
	}
	//----------------------------------------------------------------------------------------------
	private void buttonsNField()
	{
		// initiera knappar och fält 
		componentsPanel.setLayout(new BorderLayout());
		deleteButtonPanel.setLayout(new BorderLayout());
		
		final int panelWIDTH = 400;
		final int panelHEIGHT = 250;
		
		CreateSavingsButton = new JButton("Create Savings");
		createCreditButton = new JButton("Create Credits");
		getAccButton = new JButton("Get Account");
		depositButton = new JButton("Deposit");
		withdrawButton = new JButton("Withdraw");
		closeAccButton = new JButton("Close account");
		getTransactButton = new JButton("Get transactions");
		saveTransactButton = new JButton("Save transactions");
		
		saveTransactButton.setBackground(Color.GREEN);
		closeAccButton.setBackground(Color.RED);
		closeAccButton.setForeground(Color.BLACK);
		accountsNCustomersButton = new JButton("Information of bank inventory");
		
		componentsPanel.setPreferredSize(new Dimension(panelWIDTH,panelHEIGHT));
		
		buttonsPanel.add(CreateSavingsButton);
		buttonsPanel.add(createCreditButton);
		buttonsPanel.add(getAccButton);
		buttonsPanel.add(depositButton);
		buttonsPanel.add(withdrawButton);
		
		buttonsPanel.add(getTransactButton);
		
		
		deleteButtonPanel.add(saveTransactButton,BorderLayout.NORTH);
		deleteButtonPanel.add(closeAccButton,BorderLayout.SOUTH);
		final int WIDTH = 400;
		final int HEIGHT = 50;
		
		amountField = new JTextField();
		pNoField = new JTextField();
		accIdField = new JTextField();
		
		
		amountField.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		accIdField.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		pNoField.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		textInputPanel.add(amountField);
		textInputPanel.add(accIdField);
		textInputPanel.add(pNoField);
		
		componentsPanel.add(deleteButtonPanel,BorderLayout.EAST);
		componentsPanel.add(buttonsPanel,BorderLayout.NORTH);
		componentsPanel.add(textInputPanel,BorderLayout.CENTER);
		
		componentsPanel .add(accountsNCustomersButton,BorderLayout.SOUTH);
		
		amountField.setBorder(BorderFactory.createTitledBorder("Amount: "));
		accIdField.setBorder(BorderFactory.createTitledBorder("Account-ID: (Created automatically)"));
		pNoField.setBorder(BorderFactory.createTitledBorder("PNo:"));
		
		actionButtons();
		actionTextFields();// ger alla knappar och fields lyssnare
		
		// lägger alla komponenter i huvudpanelen för denna "page"
		this.add(componentsPanel,BorderLayout.NORTH);
	}
	//----------------------------------------------------------------------------------------------
	private void actionButtons() // ger alla knappar lyssnare
	{
		CreateSavingsButton.addActionListener(this);
		createCreditButton.addActionListener(this);
		getAccButton.addActionListener(this);
		depositButton.addActionListener(this);
		withdrawButton.addActionListener(this);
		closeAccButton.addActionListener(this);
		getTransactButton.addActionListener(this);
		saveTransactButton.addActionListener(this);
		accountsNCustomersButton.addActionListener(this);	
		
	}//----------------------------------------------------------------------------------------------
	private void actionTextFields() // ger alla fields lyssnare
	{
		amountField.addActionListener(this);
		pNoField.addActionListener(this);
		accIdField.addActionListener(this);
		
	}
	//----------------------------------------------------------------------------------------------
	private void resField() // initierar output fältet 
	{
		outputList = new JList();
		outputList.setLayoutOrientation(JList.VERTICAL);
		scrollPane = new JScrollPane(outputList);
		
		//----------Kod för att kunna skrolla upp/ner och sida till sida-----
		scrollPane.setPreferredSize(new Dimension(450, 200));

		scrollPane.getVerticalScrollBar().addAdjustmentListener(adjustListener);
		
		scrollPane.getHorizontalScrollBar().addAdjustmentListener(adjustListener);
		//----------------------------------------------------------------------------
		outputList.setBorder(BorderFactory.createTitledBorder("Output"));
		this.add(scrollPane); // lägger till fältet längst ner på "huvudpagen"
	}
	

}
