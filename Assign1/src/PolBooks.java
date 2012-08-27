import java.io.*;
import java.util.*;
import prefuse.data.*;
import javax.swing.JFrame;
import javax.xml.crypto.Data;

import prefuse.controls.*;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataShapeAction;
import prefuse.action.layout.*;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.data.Graph;

import prefuse.data.io.DataIOException;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.collections.IntIterator;
import prefuse.visual.EdgeItem;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

public class PolBooks {


	public static void main(String[] args) {
		Graph graph = new Graph();
		try{
			graph =  GraphParser.loadGraph(new File("data/polbooks.gml"));	
		}
		catch(Exception ex ){
			System.out.println("Error Reading From File polbooks.gml");
			System.out.println(ex);
		}



		Visualization vis = new Visualization();
		vis.addGraph("graph",graph);
		//vis.setInteractive("graph.edges",null,false);
		//LabelRenderer r = new LabelRenderer("label");
		//r.setRoundedCorner(8, 8);
		ShapeRenderer s = new ShapeRenderer(15);
		EdgeRenderer e = new EdgeRenderer(Constants.EDGE_TYPE_CURVE);
		vis.setRendererFactory(new DefaultRendererFactory(s,e));

		ActionList color = new ActionList();
		//Different Colored nodes for different value fields
		int[] palette = new int[]{ColorLib.rgb(180, 0, 0),ColorLib.rgb(0, 180, 0),ColorLib.rgb(0, 0, 180)};
		//Different colored edges
		int[] palette1 = new int[]{ColorLib.gray(180),ColorLib.gray(5)};

		//Different Shapes for different value fields
		int[] shapes = new int[]{Constants.SHAPE_RECTANGLE,Constants.SHAPE_ELLIPSE,Constants.SHAPE_TRIANGLE_UP};
		DataColorAction fill = new DataColorAction("graph.nodes", "value", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
		//ColorAction edges = new ColorAction("graph.edges",VisualItem.STROKECOLOR,ColorLib.gray(105));
		//ColorAction text = new ColorAction("graph.nodes",VisualItem.TEXTCOLOR,ColorLib.gray(5));
		DataShapeAction shape = new DataShapeAction("graph.nodes","value",shapes);
		DataColorAction edgecolor = new DataColorAction("graph.edges","type",Constants.NOMINAL,VisualItem.STROKECOLOR,palette1);
		color.add(fill);
		color.add(edgecolor);
		//color.add(text);
		color.add(shape);

		ActionList layout = new ActionList(Activity.INFINITY);
		//Force DIrected Layout
		layout.add(new ForceDirectedLayout("graph"));
		layout.add(new RepaintAction());

		vis.putAction("color",color);
		vis.putAction("layout",layout);

		Display d = new Display(vis);
		d.addControlListener(new FocusControl(1));
		d.setSize(720,480);
		d.addControlListener(new DragControl());
		d.addControlListener(new PanControl());
		d.addControlListener(new ZoomControl());
		//Adding custom control to display pop ups
	//	d.addControlListener(new Control());

		JFrame frame = new JFrame();
		frame.add(d);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		vis.run("color");
		vis.run("layout");

	}

	
	

}