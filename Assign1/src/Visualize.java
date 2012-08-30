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


public class Visualize {
	
	//constructor
	public Visualize(Graph graph){
		//UILib.setPlatformLookAndFeel();
			final Visualization vis = new Visualization();
			vis.addGraph("graph",graph);
			//vis.setInteractive("graph.edges",null,false);
			//LabelRenderer r = new LabelRenderer("label");
			//r.setRoundedCorner(8, 8);
			ShapeRenderer s = new ShapeRenderer(8);
			EdgeRenderer e = new EdgeRenderer(Constants.EDGE_TYPE_LINE);
			LabelRenderer tr = new LabelRenderer();
	        //tr.setRoundedCorner(1, 1);
	        //tr.setMaxTextWidth(2);
	        //m_vis.setRendererFactory(new DefaultRendererFactory(tr));
	        DefaultRendererFactory defaultRenderer = new DefaultRendererFactory(s,e);
	     //   defaultRenderer.add("label",tr);
			vis.setRendererFactory(defaultRenderer);
		//	vis.setRendererFactory(new DefaultRendererFactory(tr));
	

				
			//Different Colored nodes for different value fields
			int[] palette = new int[]{ColorLib.rgb(180, 0, 0),ColorLib.rgb(0, 180, 0),ColorLib.rgb(0, 0, 180)};
			//Different colored edges
			int[] palette1 = new int[]{ColorLib.gray(180),ColorLib.gray(5)};
			
			//	Different Shapes for different value fields
			int[] shapes = new int[]{Constants.SHAPE_RECTANGLE,Constants.SHAPE_ELLIPSE,Constants.SHAPE_TRIANGLE_UP};
			
			//the following is used to separate the graph.nodes based on their values and colour them accordingly
			DataColorAction nodeColour = new DataColorAction("graph.nodes", "value", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
			//ColorAction edges = new ColorAction("graph.edges",VisualItem.STROKECOLOR,ColorLib.gray(105));
			//ColorAction text = new ColorAction("graph.nodes",VisualItem.TEXTCOLOR,ColorLib.gray(5));
			DataShapeAction shape = new DataShapeAction("graph.nodes","value",shapes);
			DataColorAction edgecolor = new DataColorAction("graph.edges","type",Constants.NOMINAL,VisualItem.STROKECOLOR,palette1);
			//fill.add(VisualItem.HIGHLIGHT, ColorLib.rgb(0,200,125));
			//datacolour action is used to selectively add while colouraction is used to all
			//int hops = 30;
	        //final GraphDistanceFilter filter = new GraphDistanceFilter("graph", hops);
			nodeColour.add(VisualItem.FIXED, ColorLib.rgb(255,100,100));
	        nodeColour.add(VisualItem.HIGHLIGHT, ColorLib.rgb(255,200,125));
	        
			//action list binds all the data colour action , so we create actionlists and add to them
			ActionList color = new ActionList();			
			color.add(nodeColour);
			//color.add(new ColorAction("graph.nodes", VisualItem.FIXED, ColorLib.rgb(0,0,0)));
			//color.add(new ColorAction("graph.nodes", VisualItem.HIGHLIGHT, ColorLib.rgb(5,0,0)));
			color.add(edgecolor);
			//	color.add(text);
			color.add(shape);
			color.add(new RepaintAction());
//			color.add(filter);
			
			ActionList layout = new ActionList(Activity.INFINITY);
			//this actionlist will run indefinitely
			//Force DIrected Layout
			layout.add(nodeColour);
			layout.add(new ForceDirectedLayout("graph"));
			layout.add(new RepaintAction());
			//layout.add(filter);
	
			SizeAction highl=new SizeAction("graph.nodes",1.0);
			highl.add("_hover",3.0);
			
			ActionList highlight_neighbors=new ActionList();
			highlight_neighbors.add(highl);
			//highlight_neighbors.add(filter);
			
			
			// ---------------- Adding actionlists to the visualization
			vis.putAction("highlight_neighbors", highlight_neighbors);
			vis.putAction("color",color);
			vis.putAction("layout",layout);

			Display d = new Display(vis); // so display is bigger than visualization
			int width=720, height=480;
			d.setBackground(Color.WHITE);
			d.setSize(width,height);
			d.pan(width/2.0, height/2.0);
			d.addControlListener(new DragControl());
			d.addControlListener(new FocusControl(1));
			d.addControlListener(new PanControl());
			d.addControlListener(new ZoomControl());
			d.addControlListener(new NeighborHighlightControl());
		//	Action hoveracction=new Action();
		//	HoverActionControl hover=new HoverActionControl();
			d.addControlListener(new HoverActionControl("highlight_neighbors"));
			//Adding custom control to display pop ups
			d.addControlListener(new CustomControl());
	
			JFrame frame = new JFrame();
			frame.add(d);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			vis.run("color");
			vis.run("layout");
	
		}
			
	}

