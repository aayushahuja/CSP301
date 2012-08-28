import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import prefuse.controls.Control;
import prefuse.controls.ControlAdapter;
import prefuse.visual.EdgeItem;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

public class CustomControl extends ControlAdapter implements Control {
	    public  void itemClicked(VisualItem item, MouseEvent e)
	    {
	        if(item instanceof NodeItem)
	        {
	            String title = ((String) item.get("label"));
	            JPopupMenu jpub = new JPopupMenu();
	            jpub.add("Title: " + title);
	            jpub.show(e.getComponent(),(int) item.getX(),(int) item.getY());
	        }
	        if(item instanceof EdgeItem)
	        {

	            String type = (String)item.get("type");
	        	JPopupMenu jpub = new JPopupMenu();
	            jpub.add("Type of edge: " + type);
	            jpub.show(e.getComponent(),(int) item.getX(),(int) item.getY());
	        }
	    }
	}