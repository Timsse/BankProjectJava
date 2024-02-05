package timkes3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/*
@author Tim Åkesson, timkes-3
*/
// 
abstract class Panel extends BankGUI_Components
{

	protected JPanel componentsPanel = new JPanel();
	protected JPanel buttonsPanel = new JPanel();
	protected JPanel textInputPanel = new JPanel();
	protected JPanel deleteButtonPanel = new JPanel();
	
	protected JList outputList;
	protected JScrollPane scrollPane;
	protected CustomAdjustmentListener adjustListener = new CustomAdjustmentListener();// till alla paneler som behöver en ScrollBar

	
	Panel()
	{
	this.setLayout(new BorderLayout());
	
	final int FRAME_WIDTH = 850;
	final int FRAME_HEIGHT = 600;
	this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
	}

}



class CustomAdjustmentListener implements AdjustmentListener 
{ // till scrollpane
	
	
	public void adjustmentValueChanged(AdjustmentEvent e) 
    {
    	{  
            e.getAdjustable().setValue(e.getAdjustable().getValue());  
        }  
    }
}