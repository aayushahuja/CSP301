import java.awt.Color;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataShapeAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.io.DataIOException;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;


public class Visualize {
	
	//constructor
	public Visualize(Graph graph){
		//	Calculate all the edge types and other triad details
	//	evaluate();
			Visualization vis = new Visualization();
			vis.addGraph("graph",graph);
			//vis.setInteractive("graph.edges",null,false);
			//LabelRenderer r = new LabelRenderer("label");
			//r.setRoundedCorner(8, 8);
			ShapeRenderer s = new ShapeRenderer(8);
			EdgeRenderer e = new EdgeRenderer(Constants.EDGE_TYPE_LINE);
			vis.setRendererFactory(new DefaultRendererFactory(s,e));
	
			ActionList color = new ActionList();
			//Different Colored nodes for different value fields
			int[] palette = new int[]{ColorLib.rgb(180, 0, 0),ColorLib.rgb(0, 180, 0),ColorLib.rgb(0, 0, 180)};
			//Different colored edges
			int[] palette1 = new int[]{ColorLib.gray(180),ColorLib.gray(5)};
	
			//	Different Shapes for different value fields
			int[] shapes = new int[]{Constants.SHAPE_RECTANGLE,Constants.SHAPE_ELLIPSE,Constants.SHAPE_TRIANGLE_UP};
			DataColorAction fill = new DataColorAction("graph.nodes", "value", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
			//ColorAction edges = new ColorAction("graph.edges",VisualItem.STROKECOLOR,ColorLib.gray(105));
			//ColorAction text = new ColorAction("graph.nodes",VisualItem.TEXTCOLOR,ColorLib.gray(5));
			DataShapeAction shape = new DataShapeAction("graph.nodes","value",shapes);
			DataColorAction edgecolor = new DataColorAction("graph.edges","type",Constants.NOMINAL,VisualItem.STROKECOLOR,palette1);
			color.add(fill);
			color.add(edgecolor);
			//	color.add(text);
			color.add(shape);
	
			ActionList layout = new ActionList(Activity.INFINITY);
			//Force DIrected Layout
			layout.add(new ForceDirectedLayout("graph"));
			layout.add(new RepaintAction());
	
			vis.putAction("color",color);
			vis.putAction("layout",layout);
	
			Display d = new Display(vis);
			int width=720, height=480;
			d.setBackground(Color.DARK_GRAY);
			d.setSize(width,height);
			d.pan(width/2.0, height/2.0);
			d.addControlListener(new DragControl());
			d.addControlListener(new PanControl());
			d.addControlListener(new ZoomControl());
			//Adding custom control to display pop ups
			//d.addControlListener(new CustomControl());
	
			JFrame frame = new JFrame();
			frame.add(d);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			vis.run("color");
			vis.run("layout");
	
		}
			
	}
