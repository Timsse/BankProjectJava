package timkes3;

import java.math.BigDecimal;

/*
@author Tim Åkesson, timkes-3
*/

public class CreditAccount extends Account
{
	
	// instansvariabler
	private BigDecimal interestDept = new BigDecimal(7);
	private BigDecimal interestReturn = new BigDecimal(0.5);
	
	
	
	
	public CreditAccount()
	{
		// ger detta konto typen "kreditkonto" då saldot är 0 vid skapandet av konto är räntan på 0.5
		accType = "Kreditkonto";
		interest = interestReturn;
	}	
		
	
	//---------------------------------------------------------------------------------------------------------------------------	

	public boolean withdraw(double out)
	{
		boolean state = false;
		BigDecimal outBig = new BigDecimal(out);
		double balanceDouble = balance.doubleValue();
		
		
			if (balanceDouble-out >= -5000 && out > 0) // uttag måste vara mer än 0 och inte passera gränsen på -5000 kr i saldo
				{
				balance = balance.subtract(outBig);
				
				state=true;
				if (balanceDouble < 0) // om saldot efter uttaget blir mindre än 0 ändras räntan till 7%
					{
					interest = interestDept;
					}
				}
			
		return state;
	}
	//---------------------------------------------------------------------------------------------------------------------------	

	public boolean deposit(double in)
	{
		BigDecimal bigIn = new BigDecimal(in);
		boolean state;
		double balanceDouble = balance.doubleValue();
		
		if (in <= 0)   // instättning måste vara större än 0
		{
			state = false;
		}
		else
		{
			balance = balance.add(bigIn);
			
			if (balanceDouble >= 0)  // om saldot är 0 eller mer sätts ränta till 0.5 
			{
				interest = interestReturn;
			}
			state = true;
		}
		
		return state;
	}
	
	
}
