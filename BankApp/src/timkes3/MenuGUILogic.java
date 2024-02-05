package timkes3;


import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JOptionPane;


import javax.swing.JFileChooser;

/*
@author Tim Åkesson, timkes-3
klassen hanterar "logiken" för JFileChooser mellan bankLogic och menyn i BankGUI_main
*/
public class MenuGUILogic extends BankGUI_Components {

	private static JFileChooser startFile = new JFileChooser("Timkes3_files");;
	
	
	MenuGUILogic(String text) // konstruktorn används i BankGUImain och får värdena Save bank eller load bank
	{
		actionPerformed(text);	// argumentet skickas vidare 
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------
	public void actionPerformed(String text) 	// text = argumenten från klassen BankGUI_Frame_Menu
	{
		//---------------------------------------------------------------------------------------------------------------------------
		if(text.equals("Save Bank"))
		{
			try
			{
				
				int respons = startFile.showSaveDialog(null); // välj fil som ska öppnas genom dialogen
				
				if (respons == JFileChooser.APPROVE_OPTION)		// Gick filena att välja provar metoden i banklogic att spara allt i fil
				{
					File file = new File(startFile.getSelectedFile().getAbsolutePath());
					
					int state = bank.saveCustomers(file);
					if(state ==1) // 1 = metoden saveCustomers gick igenom
					{	
						JOptionPane.showMessageDialog(null, "sucess");
					}
				}
				
			}
			catch(RuntimeException ex)
			{
				JOptionPane.showMessageDialog(null, "Something went wrong!");
			}
		}
		
		//---------------------------------------------------------------------------------------------------------------------------
		if(text.equals("Load Bank"))
		{
			try
			{
				
				int respons = startFile.showOpenDialog(null); // välj fil som ska öppnas genom dialogen
				if (respons == JFileChooser.APPROVE_OPTION)	//  Gick filena att välja provar metoden i banklogic att ladda allt från fil
				{
					File selectedFile = new File(startFile.getSelectedFile().getAbsolutePath());
					
				
					int state= bank.loadCustomers(selectedFile);
					if(state ==1)	 //1 = metoden loadCustomers gick igenom
					{	
						JOptionPane.showMessageDialog(null, "sucess");
					}
				}
				
			}
			catch(RuntimeException ex)
			{
				JOptionPane.showMessageDialog(null, "Something went wrong!");
			}
		}
		
		
	}



	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
