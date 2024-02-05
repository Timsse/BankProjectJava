package timkes3;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;



/*
 @author Tim Åkesson, timkes-3
*/

public class BankLogic 
{
	private ArrayList<Customer> allCustomers; //Lista för all "customerobjekt"
	
	
	
		
	BankLogic()
	{
		allCustomers = new ArrayList<Customer>();
	}

	//---------------------------------------------------------------------------------------------------------------------------	
	//------privata metoder------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------
	public boolean checkForPNo(String pNo)// var privat innan
	{
		
		boolean state = false;
		for (Customer customers : allCustomers)
		{
			if (customers.getPNo().equals(pNo) == true)
			{
				state = true;
				break;
			}
		}
		return state;
	}
	//---------------------------------------------------------------------------------------------------------------------------
	private ArrayList<Customer> getList()
	{
		return allCustomers;
	}
	private Customer findCustomer(String pNo)
	{
		
		Customer client = null;	
		for (Customer customer : allCustomers)
		{
			if (customer.getPNo().equals(pNo) == true)
			{
				client = customer;	
				break;
			}
		}
		return client;
		}
	//---------------------------------------------------------------------------------------------------------------------------	
	private void delete(String pNo)
	{
		
		for (Customer customer:allCustomers)
		{
			if (customer.getPNo().equals(pNo) == true)
			{
				allCustomers.remove(customer);
				break;
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------------------	
	//-------------Huvudmetoder för BankLogic------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------	
	
	
	
	//Metod som hämtar alla medlemmar och lägger dem i en egen ArrayList
		public ArrayList<String> getAllCustomers() 
		{
			ArrayList<String> allCustomersString = new ArrayList<String>();
			for (Customer customers : allCustomers)
			{
				String line = customers.getPNo()+" "+customers.getName() +" "+ customers.getSurname();
				allCustomersString.add(line);
			}
			return allCustomersString;	
		}
		
	//---------------------------------------------------------------------------------------------------------------------------	
	public boolean createCustomer(String name, String surname, String pNo)
	{
		boolean state = false;
		if (checkForPNo(pNo) == false && pNo.length()==10 && name.length() > 0 && surname.length() > 0)
		{
			allCustomers.add(new Customer(name, surname, pNo));
			state = true;
		}
		
		return state;
	}
	//---------------------------------------------------------------------------------------------------------------------------
	public ArrayList<String> getCustomersAndAccounts()
	{
		ArrayList<String> infoList = new ArrayList<String>();
		for (Customer customer :allCustomers)
		{
			String info = "pNo: ";
			info += customer.getPNo();
			info += ". Accounts: ";
			for(Account acc: customer.getAccForMedlem())
			{
				info += acc.accountValues();
				info += ", ";
			}
			infoList.add(info);
		}
		return infoList;
	}
	
	public ArrayList<String> getCustomer(String pNo)
	{
		ArrayList<String> info = new ArrayList<String>();//lista med unik kund och om denne har konton läggs dem till efter info om kund.
		
		
		for (Customer customer : allCustomers)
		{
			if (customer.getPNo().equals(pNo) == true && allCustomers.size()!=0)
			{
				String y = customer.getPNo();
				String o = customer.getName();			// kunden läggs till i listan infos första index.
				String u = customer.getSurname();
				String you = y+" "+o+" "+u;
				info.add(you);
				
				for (Account acc :customer.getAccForMedlem())
				{
					 info.add(customer.accountInfo(acc.getAccNum()));	// har kunden konton läggs dem till i listan info med efter index 0.
				}
			}
		}
		if (info.size()==0)
		{
			info = null;
		}
		return info;		//listan info returneras med medlem och dess konto om medlem inte finns retuneras null.
		
	}
	//---------------------------------------------------------------------------------------------------------------------------
	public boolean changeCustomerName(String name, String surname, String pNo)
	{
		
		boolean state= false;
		for (Customer customer : allCustomers)	// loop som går igenom alla kunder
		{
			if (customer.getPNo().equals(pNo) == true && name.isEmpty() == false && surname.isEmpty() == false)	// Om personnummret finns och alla argument har tecken i sig ändas både namn och efternamn.
			{
				customer.setFitstName(name);
				customer.setLastName(surname);
				state = true;
				break;
			}
			if (name.isEmpty() == true && customer.getPNo().equals(pNo) == true && surname.isEmpty() == false)	// om personnummret finns och argumentet efternamn har tecken i sig ändras efternamn och förnamn ändras ej.
			{
				customer.setLastName(surname);
				
				state=true;
			}
			if (surname.isEmpty() == true && customer.getPNo().equals(pNo) == true && name.isEmpty() == false) 	// samma som ovan fast med förnamn istället för efternamn
			{
				customer.setFitstName(name);
				
				state=true;
			}
		}
		return state;	//returnerar true om något namn ändrats eller false om inget namn ändrats
		
	}
	//---------------------------------------------------------------------------------------------------------------------------
	public int createSavingsAccount(String pNo)
	{
		
		int value = -1;
		
		for (Customer customer : getList())
		{
			if (customer.getPNo().equals(pNo) == true)
			{
				customer.newSavingsAcc();
				for (Account acc : customer.getAccForMedlem())
				{
					value = acc.getAccNum(); // value fångar upp kundens senaste kontonummer på sista varvet i loopen 
											 // vilket också är det nyaste kontot som precis skapats och finns sist i liastan "getAccforMedlem"
				}
			}
		}
		return value; //returnerar -1 om kund inte finns annars returneras kontonummret som precis skapats för kunden
	}

	//---------------------------------------------------------------------------------------------------------------------------
	public int createCreditAccount(String pNo)
	{
		
		int value = -1;
		
		
		for (Customer customer : allCustomers)
		{
			
			if (customer.getPNo().equals(pNo) == true)
			{
				
				customer.newCreditAcc();
				for (Account acc : customer.getAccForMedlem())
				{
					value = acc.getAccNum(); // value fångar upp kundens senaste kontonummer på sista varvet i loopen 
											 // vilket också är det nyaste kontot som precis skapats och finns sist i liastan "getAccforMedlem"
				}
			}
		}
		
		return value; //returnerar -1 om kund inte finns annars returneras kontonummret som precis skapats för kunden
	}
	//---------------------------------------------------------------------------------------------------------------------------
	public String getAccount(String pNo, int accountId)
	{
		
		String string = null;
		
		for (Customer cust: allCustomers)
		{
			if (cust.accountInfo(accountId) == null)
			{
				continue;
			}
			else
			{
				Customer customer = findCustomer(pNo);
				string = customer.accountInfo(accountId);
			}
		}
		/*if(findCustomer(pNo)==null) // finns inte kunden får en variabel värdet null.
		{
			string = null;
		}
		else 	// Finns kunden får variabeln värdet och kontot får variabeln info om kontot
		{
			Customer customer = findCustomer(pNo);
			string = customer.accountInfo(accountId);		// här retunerar metoden från klassen Customer null om kontot inte finns
		}*/
		return string;
		
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	public boolean deposit(String pNo, int accountId, int amount)
	{
		boolean state = true;
		
		if (findCustomer(pNo)== null || (findCustomer(pNo).deposit(accountId, amount) == false)) // finns kund och insättningen går igenom metoden deposit från klassen Customer kommer true returneras
		{
			 
				state = false;
		}
		
		
		return state;
	}
	//---------------------------------------------------------------------------------------------------------------------------
	public boolean withdraw(String pNo, int accountId, int amount)
	{
		boolean state = true;
		if(findCustomer(pNo)== null || (findCustomer(pNo).withdraw(accountId, amount) == false))  // finns kund och insättningen går igenom metoden withdraw från klassen Customer kommer true returneras
		{
			state = false;
		}
			
		return state;
	}
	//---------------------------------------------------------------------------------------------------------------------------
	public String closeAccount(String pNo, int accountId)
	{
		String string;
		
		if(findCustomer(pNo)==null )		// finns inte kunden kommer null returneras
		{
			string = null;
		}
		else		// finns kunden hämtas kunden och en metod från klassen Customer stänger kontot om det finns, annars retuneras null
		{
			Customer customer = findCustomer(pNo);
			string = customer.closedAccountInfo(accountId);
			
		}
		return string;
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------
	public ArrayList<String> deleteCustomer(String pNo)
	{
		ArrayList<String> list = new ArrayList<String>(); // lista med information om kund som tas bort
		ArrayList<Integer> accountId = new ArrayList<Integer>(); // lista som gör den sista for-loopen möjlig då rangen inte får ändras
		if(findCustomer(pNo)==null)
		{
			list = null;
		}
		else
		{
			Customer customer = findCustomer(pNo);// hitta kunden som skall tas bort
			String member = customer.getPNo()+" "+customer.getName()+" "+customer.getSurname();
			list.add(member);// lägger till medlem i listan list
			
			for (Account acc :customer.getAccForMedlem())	// hämtar ingformation om alla konton som skall tas bort och lägger det i listan list
			{
				accountId.add(acc.getAccNum());	
				
			}
			for (int i=0; i < accountId.size();i++)		// tar bort alla konton som kunden har. kan vara onödigt då objektet "kund" tas bort senarer och kanske dess innehåll
			{
				int element = accountId.get(i);
				list.add(customer.closedAccountInfo(element));
			}
				
			delete(pNo);	// här tas kunden bort genom den privata metoden "delete"
		}
		return list;	// en lista retuneras med information om kund och dess konton
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------

	public ArrayList<String> getTransactions(String pNo, int accountId)
	// 
	{
		
		ArrayList<String> res = null;
		for (Customer customer : allCustomers )
		{
			if (customer.getPNo().equals(pNo) == true)
			{
				res = customer.getTransactions(accountId);
			}
		}
		return res;
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------

	//-------------------------------SAVE to file----------------------------------//
	
		public int saveCustomers(File fileName) // spara fil till dator
		{
			int state= 0;
			try
			{
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
				
				
				out.writeInt(Account.latestNum); // sparar det senaste kontonumret 
				out.writeObject(this.allCustomers);// sparar alla objekt
				out.flush();// don't know
					
				 state= 1;
				out.close();
			}
			catch(IOException e)
			{
				System.out.println("Det gick inte skriva till filen: " + fileName);
			}
			return state; 	//retuneras 0 vet metoder som använder denna metod att något gick fel
		}
		
		
		//---------------------------------------------------------------------------------------------------------------------------
		public int saveTransactions(File fileName,String pNo,int accountId)
		{
			int state= 0;
			try
			{
				PrintWriter in = new PrintWriter(fileName);
				
				SimpleDateFormat dateNTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String strDateNTime = dateNTime.format(date);
				Customer cust = findCustomer(pNo);
				in.println("File created: "+strDateNTime+".\nInfo: "+cust.accountInfo(accountId) +"\n"); // skriver datum och information om kontot högst upp i filen
				
				for (int i = 0; i<getTransactions(pNo,accountId).size(); i++)
				{
					in.println(getTransactions(pNo,accountId).get(i));	// skriver in rad för rad till filen
					
				}
				state = 1;
				in.close();
			}
			catch(Exception e)
			{
				System.out.println("Det gick inte skriva till filen: " + fileName);
				JOptionPane.showMessageDialog(null, "Something went wrong!");
			}
			return state;	//retuneras 0 vet metoder som använder denna metod att något gick fel
		}
	//-------------------------------LOAD to file----------------------------------//	
		
		
		public int loadCustomers(File fileName) 
		{
			int state= 0;	
			
			allCustomers.clear();// tar bort befintlig lista och därmed försvinner allt som inte sparats för att sedan ladda in en fil
			try
			{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
				Account.latestNum = in.readInt();// ger klassvariabeln i klass account det senasete kontonumret från den sparade filen
				this.allCustomers = (ArrayList<Customer>) in.readObject(); // läser in alla objekt till listan
				
				
					
				state=1;
				in.close();
			}
			catch(Exception e)
			{
				System.out.println("Det gick inte hämta från filen: " + fileName);
				JOptionPane.showMessageDialog(null, "Something went wrong!");
			}
			return state;	//retuneras 0 vet metoder som använder denna metod att något gick fel
		}
		
		
	






}



	