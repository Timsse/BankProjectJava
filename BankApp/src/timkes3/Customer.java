package timkes3;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/*
 @author Tim Åkesson, timkes-3
*/



public class Customer implements Serializable
{
	
	
	
	private ArrayList<Account> accounts = new ArrayList<Account>();//Lista för alla "Accountobjekt"
	private String name;
	private String surname;
	private String pNo;
	
 
	
	
//---------------------------------------------------------------------------------------------------------------------------
	
	// konstruktor 
	public Customer(String name, String surname,String pNo) 
	{
		
		
		this.name= name;
		this.surname = surname;
		this.pNo = pNo;
		
	}
//---------------------------------------------------------------------------------------------------------------------------	
//---------------------------------------------------------------------------------------------------------------------------	
	
	public String getPNo()
	{
		return pNo;
	}
	
	
	public String getName()
	{
		return name;
	}
	public String getSurname()
	{
		return surname;
	}
	public void setFitstName(String name)
	{
		this.name = name;
	}
	
	
	public void setLastName(String surname)
	{
		this.surname = surname;
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------	
	/*
	 * Metoden skapar ett nytt konto(genom klass Account)
	 * detta läggs i listan med konton för individuell kund
	 */
	public void newSavingsAcc()
	{
		accounts.add(new SavingsAccount());	
	}
	public ArrayList<Account> getAccForMedlem()
	{
		return accounts;
	}
	
	
	public void newCreditAcc()
	{
		accounts.add(new CreditAccount());	
	}
	//---------------------------------------------------------------------------------------------------------------------------	
	public boolean deposit(int accID, double in)
	// metod för kund som sätter in pengar, om ett insättning hade kostat pengar ändras fee under förhållanden som tillkomma
	{
		double fee = 0;
		boolean state= false;
		for (Account acc : accounts)
		{
			if (acc.getAccNum() == accID && in > 0) // om ett befintligt kontonummer matchar argumentet accID samt in är större än noll sker en deposition i det kontot.
			{
				
				acc.deposit(in);
				acc.addTransactions(in, "",fee); // metoden som lägger in insättningen i en lista måste ha ett argument fee, därför finns fee med i denna metod.
				state = true;
			}
		}
		return state;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------	
	public boolean withdraw(int accID, double out)
	// metod för kund som tar ut pengar, om ett uttag kostat pengar ändras fee till kontots utagsavgift i procents

	{
		double fee = 0;
		boolean state = false;
		for (Account acc : accounts)
		{
			if (acc.getAccNum() == accID && acc.withdraw(out) == true) // om ett befintligt kontonummer matchar argumentet accID samt out är mindre än balance men större än 0 sker en withdraw.
			{	
				if (acc.accType == "Sparkonto")
				{
					
					if (((SavingsAccount) acc).getFeeState() == true) // om status för avgiften är true
					{
						fee = ((SavingsAccount) acc).getInterestFee(); // metod från account som ger fee uttagsavgiften i % 
					}
				}	
				state = true;
				acc.addTransactions(out, "-",fee);
			}
		}
		return state;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------	
	
	public String accountInfo(int accID)
	// retunerar en sträng med info om kontot 
	{
		String text = null;
		for (Account acc : accounts)
		{
			if (acc.getAccNum() == accID)//klassen account används för att se om ID finns och isf ge strängen res dess värden
			{
				NumberFormat percentFormat = NumberFormat.getPercentInstance(new Locale("sv","SE"));
				percentFormat.setMaximumFractionDigits(2); // Anger att vi vill ha max 1 decimal
				String id = String.valueOf(acc.getAccNum())+" ";
				String bal = NumberFormat.getCurrencyInstance(new Locale("sv","SE")).format(acc.getBalance());
				
				String type = acc.getAccType();
				
				
				String interestString = percentFormat.format(acc.getInterest()/100);
				
				text = id+bal+" "+type+" "+interestString;
			}
		}
		return text;
	}
	//---------------------------------------------------------------------------------------------------------------------------	
	public String closedAccountInfo(int accID)
	// Retunerar en lista för ett angivets konto fylld med dess händelser uttag och insättning
	{
		
		
		String res = null;
		for (Account acc : accounts)
		{
			if (acc.getAccNum() == accID) // hittar kontot som ska redovisas
			{
				String interest = acc.calcInterest();
				String accIDString = String.valueOf(acc.getAccNum());
				String accType = acc.getAccType();
				String balanceString =NumberFormat.getCurrencyInstance(new Locale("sv","SE")).format(acc.getBalance());
				res = (accIDString+" "+balanceString+" "+accType+" "+interest);
				
				accounts.remove(acc); 
				
				break;
			}
		}	
		
		return res;// ger en sträng med all info vi vill åt		
	}
	
	public ArrayList<String> getTransactions(int accID)
	// Retunerar en lista för ett angivets konto fylld med dess händelser uttag och insättning
	{
		ArrayList<String> res = null;
		for (Account acc : accounts)
		{
			if (acc.getAccNum() == accID) // hittar kontot som ska redovisas
			{
				
				res = acc.getTransactions();
				if (res.size() == 0)
				{
					
					res.isEmpty();
				}
			}
		}
		return res;
	}
	
	
//---------------------------------------------------------------------------------------------------------------------------	
//---------------------------------------------------------------------------------------------------------------------------	
//---------------------------------------------------------------------------------------------------------------------------	

}
