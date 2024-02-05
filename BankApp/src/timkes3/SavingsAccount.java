package timkes3;
import java.math.BigDecimal;
/*
@author Tim Åkesson, timkes-3
*/
public class SavingsAccount extends Account{

	// instansvariabler
	private BigDecimal withdrawInterest = new BigDecimal(0.02); // kostnad för ett uttag efter första uttaget
	private int counter = 0; // räknare för antal uttag
	private boolean fee; // avgör om en avgift sker eller ej
	
	public SavingsAccount() 
	{
		// ger detta konto typen "Sparkonto" då saldot är 0 vid skapandet av konto och räntan på 1.2 på detta konto alltid
		accType = "Sparkonto";
		interest = new BigDecimal(1.2);
	}
	
	//---------------------------------------------------------------------------------------------------------------------------	

	public boolean withdraw(double out)
	{
		BigDecimal bigDouble = new BigDecimal(out);
		boolean state = false; // tillstånd om uttag blir utförd eller ej.
		double balanceDouble = balance.floatValue();
		double interestDouble = withdrawInterest.doubleValue();
		
		if (counter == 0)
			{
			fee = false; // vid första uttaget sker ingen avgift
			if (out <= balanceDouble && out >= 0)
				{
				balance = balance.subtract(bigDouble);
				
				state=true;
				}
			}
		
		if (counter != 0) //efter första uttaget läggs avgiften till på alla uttag
			{
		
			if ((out+out * interestDouble) <= balanceDouble && out >= 0)
				{
				balance = balance.subtract(bigDouble.add(bigDouble.multiply(withdrawInterest))) ;
				fee = true; // avgiftstatus blir sann
				state=true;
				}
			}
		counter++;//räknaren adderar 1 efter varje uttag
		return state;
	}
	//---------------------------------------------------------------------------------------------------------------------------	

	public boolean deposit(double in)
	{
		BigDecimal bigIn = new BigDecimal(in);
		boolean state;
		
		if (in <= 0)
		{
			state = false;
		}
		else
		{
			balance = balance.add(bigIn);
			state = true;
		}
		
		return state;
	}
	//---------------------------------------------------------------------------------------------------------------------------	

	public boolean getFeeState()
	{
		return fee;
	}
	//---------------------------------------------------------------------------------------------------------------------------	

	public double getInterestFee()
	{
		double interestDouble = withdrawInterest.doubleValue();
		return interestDouble;
	}


	
	
}