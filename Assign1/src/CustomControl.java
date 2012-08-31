import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import prefuse.controls.Control;
import prefuse.controls.ControlAdapter;
import prefuse.visual.EdgeItem;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

//This class is used for displaying information in a popup when the user clicks on an edge or node of the 
//visualization
public class CustomControl extends ControlAdapter implements Control {
	
	    public  void itemClicked(VisualItem item, MouseEvent e)
	    {
	        if(item instanceof NodeItem)
	        {
	            String title = ((String) item.get("label"));
	            String value = ((String) item.get("value"));
	            JPopupMenu jpub = new JPopupMenu();
	            jpub.add("Title: " + title);
	            jpub.add("Value: " + value);
	            jpub.show(e.getComponent(),10,10);
	        }
	        if(item instanceof EdgeItem)
	        {

	            String type = (String)item.get("type");
	        	JPopupMenu jpub = new JPopupMenu();
	            jpub.add("Type of edge: " + type);
	            jpub.show(e.getComponent(),10,10);
	        }
	    }
	}