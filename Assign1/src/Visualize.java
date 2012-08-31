import java.awt.Color;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.SizeAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataShapeAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
//import prefuse.action.layout.CircleLayout;
//import prefuse.action.layout.graph.TreeLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.HoverActionControl;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.io.DataIOException;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.ui.UILib;
import prefuse.visual.VisualItem;

//This class creates the visualization 
//It takes a graph as input to its constructor and makes a JFrame showing the visualization of the graph
public class Visualize {

	//constructor
	public Visualize(Graph graph){
			final Visualization vis = new Visualization();
			//adding graph to the visualization
			vis.addGraph("graph",graph);
			ShapeRenderer s = new ShapeRenderer(8);
			//Edges are chosen as  lines
			EdgeRenderer e = new EdgeRenderer(Constants.EDGE_TYPE_LINE);
			LabelRenderer tr = new LabelRenderer();
	        DefaultRendererFactory defaultRenderer = new DefaultRendererFactory(s,e);
	        //setthing the renderer for the visualization
			vis.setRendererFactory(defaultRenderer);



			//Different Colored nodes for different value fields
			//palette is for Node Colours , three colours for three types of nodes 
			int[] palette = new int[]{ColorLib.rgb(150, 100, 100),ColorLib.rgb(100, 150, 100),ColorLib.rgb(100, 100, 150)};
			//hoverPalette is for colour when mouse is over a node
			int[] hoverPalette = new int[]{ColorLib.rgb(200, 0, 0),ColorLib.rgb(0, 200, 0),ColorLib.rgb(0, 0, 200)};
			//neighbour is for colours of the neighbours of the node under the mouse
			int[] neighbourPalette = new int[]{ColorLib.rgb(255, 0, 0),ColorLib.rgb(0, 255, 0),ColorLib.rgb(0, 0, 255)};
			//Two colours for the two types of edges 
			int[] palette1 = new int[]{ColorLib.gray(50),ColorLib.gray(100)};

			//	Different Shapes for different value fields of the nodes
			int[] shapes = new int[]{Constants.SHAPE_RECTANGLE,Constants.SHAPE_ELLIPSE,Constants.SHAPE_TRIANGLE_UP};

			//the following is used to separate the graph.nodes based on their values and colour them accordingly
			DataColorAction nodeColour = new DataColorAction("graph.nodes", "value", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
			DataShapeAction shape = new DataShapeAction("graph.nodes","value",shapes);
			DataColorAction edgecolor = new DataColorAction("graph.edges","type",Constants.NOMINAL,VisualItem.STROKECOLOR,palette1);
			//datacolour action is used to selectively add while colouraction is used to all
			//the following two lines are used when the mouse hovers on a node
			nodeColour.add(VisualItem.FIXED , new DataColorAction("graph.nodes", "value", Constants.NOMINAL, VisualItem.FILLCOLOR, hoverPalette));
			nodeColour.add(VisualItem.HIGHLIGHT , new DataColorAction("graph.nodes", "value", Constants.NOMINAL, VisualItem.FILLCOLOR, neighbourPalette));

			//action list binds all the data colour action 
			ActionList color = new ActionList();			
			color.add(nodeColour);
			color.add(edgecolor);
			color.add(shape);
			color.add(new RepaintAction());

			//this actionlist will run indefinitely
			ActionList layout = new ActionList(Activity.INFINITY);
			layout.add(nodeColour);
			ForceDirectedLayout fdl=new ForceDirectedLayout("graph");
			fdl.setMaxTimeStep(100L);
			layout.add(fdl);
			layout.add(new RepaintAction());
			
			//the size of the nodes increase when mouse is over them
			SizeAction enlarge=new SizeAction("graph.nodes",1.0);
			enlarge.add("_hover",3.0);

			ActionList hover=new ActionList();
			hover.add(enlarge);


			// ---------------- Adding actionlists to the visualization
			vis.putAction("hover", hover);
			vis.putAction("color",color);
			vis.putAction("layout",layout);

			Display d = new Display(vis); // so display is bigger than visualization
			int width=1200, height=800;
			d.setBackground(Color.GRAY); // colour of the background 
			d.setSize(width,height);
			d.pan(width/2.0, height/2.0); 
			d.addControlListener(new DragControl());
			d.addControlListener(new FocusControl(1));
			d.addControlListener(new PanControl());
			d.addControlListener(new ZoomControl()); 
			d.addControlListener(new NeighborHighlightControl()); // used for highlighting the neighbours when mouse is over a node
			d.addControlListener(new HoverActionControl("hover"));
			d.addControlListener(new CustomControl());
			
			//JFrame containg the visualization
			JFrame frame = new JFrame();
			frame.add(d);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			vis.run("color");
			vis.run("layout");

		}

	}
