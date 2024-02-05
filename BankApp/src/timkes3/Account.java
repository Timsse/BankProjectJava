package timkes3;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.Locale;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.math.BigDecimal;

/*
 @author Tim Åkesson, timkes-3
*/

abstract class Account implements Serializable {
	
	
	// instansvariabler som är gemensamma för alla sorters konton
	protected BigDecimal interest;
	protected String accType;
	protected BigDecimal balance = new BigDecimal(0);
	private int accID;
	public static int latestNum = 1000;// public då klassen banklogics metoder"save" och"load" hämtar klass variabeln för att lägga och hämta från fil
	private ArrayList<String> accountTransactions = new ArrayList<String>();
	 
	
	
	public Account()
	{
		
		//ser till så att ett kontonummer bara kan deklareras en gång
	latestNum++;
	accID = latestNum;
	
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------	
	// metoder för transaktioner ut och in
	public abstract boolean withdraw(double out);
	
	
	
	//---------------------------------------------------------------------------------------------------------------------------	
	public abstract boolean deposit(double in );
	
	
	
	
	
	// hämtar angiven ränta för vardera konto
	public  double getInterest()
	{
		double balanceInterest = interest.doubleValue();
		return balanceInterest;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	// metod för att se sitt unika kontonummer
	public int getAccNum()
	{
		return accID;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	// lista med alla transaktioner 
		public ArrayList<String> getTransactions()
		{
			return accountTransactions;
		}
	//---------------------------------------------------------------------------------------------------------------------------
	public String getAccType()
	{
		return accType;
	}
	public double getBalance()
	{
		double doubleBalance = balance.doubleValue();
		return doubleBalance;
	}
	//---------------------------------------------------------------------------------------------------------------------------	
	public String calcInterest()
	{
		double balanceDouble = balance.doubleValue();
		double interestDouble = interest.doubleValue();
		NumberFormat percentFormat = NumberFormat.getCurrencyInstance(new Locale("sv","SE"));
		percentFormat.setMaximumFractionDigits(2); // Anger att vi vill ha max 1 decimal
		String percentStr = percentFormat.format(balanceDouble*(interestDouble/100));
		

		return percentStr;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------
	

	public String accountValues()
	// retunerar en sträng med information om angivet konto konto ID, saldo, kontotyp och räntekostnad/ räntevinnst
	{
		String id = String.valueOf(accID)+" ";
		

		String bal = NumberFormat.getCurrencyInstance(new Locale("sv","SE")).format(balance)+" ";
		String type = accType+" ";
		String calcInterest= (calcInterest());
		
		
		
		String text = id+bal+type+calcInterest;
				
		return text;
	}
	
		
		
		//---------------------------------------------------------------------------------------------------------------------------	
		public void addTransactions(double amount, String inOrOut,double interest)
		// hämtar datum och tid tillsammans med transaktion och saldo.
		{
			SimpleDateFormat dateNTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String strDateNTime = dateNTime.format(date);
			String transaction =  NumberFormat.getCurrencyInstance(new Locale("sv","SE")).format(amount+amount*interest);
			String balanceAfter= NumberFormat.getCurrencyInstance(new Locale("sv","SE")).format(balance);
			accountTransactions.add(strDateNTime+" "+inOrOut+transaction+" Saldo: "+balanceAfter);
		}
		
	//---------------------------------------------------------------------------------------------------------------------------	
	//---------------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------------	
	
}
